package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.refund.*;
import com.nadeem.changejar.kiranaregister.entity.*;
import com.nadeem.changejar.kiranaregister.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final RefundItemRepository refundItemRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final StoreTransactionRepository storeTransactionRepository;
    private final InventoryRepository inventoryRepository;

    @Transactional
    @Override
    public CreateRefundResponse createRefund(CreateRefundRequest request) {
        UUID transactionId = UUID.fromString(request.getTransactionId());

        // 1. Validate the transaction exists
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + request.getTransactionId()));

        // 2. Verify the authenticated user is the one who made the original transaction
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = auth.getName();
        if (!transaction.getUserId().equals(currentUserId)) {
            throw new RuntimeException("You are not authorized to request a refund for this transaction");
        }

        // 3. Fetch original transaction items, keyed by productId
        List<TransactionItem> txItems = transactionItemRepository.findByTransactionId(transactionId);
        Map<String, TransactionItem> txItemsByProduct = txItems.stream()
                .collect(Collectors.toMap(TransactionItem::getProductId, item -> item));

        // 4. Fetch existing refunds for this transaction to check already-refunded quantities
        List<Refund> existingRefunds = refundRepository.findByTransactionId(request.getTransactionId());
        Map<String, Integer> alreadyRefundedQty = existingRefunds.stream()
                .filter(r -> !"REJECTED".equals(r.getStatus()))
                .flatMap(r -> r.getItems().stream())
                .collect(Collectors.groupingBy(
                        RefundItem::getProductId,
                        Collectors.summingInt(RefundItem::getQuantityRefunded)
                ));

        // 5. Create the Refund entity
        Refund refund = new Refund();
        refund.setTransactionId(request.getTransactionId());
        refund.setPaymentCurrency(transaction.getPaymentCurrency());
        refund.setReason(request.getReason());
        refund.setStatus("PENDING");
        refund.setCreatedAt(Instant.now());

        BigDecimal totalRefundPayment = BigDecimal.ZERO;
        String storeId = null;

        // 6. Process each refund item using the original transaction's exchange rate
        for (RefundItemRequest itemRequest : request.getItems()) {
            String productId = itemRequest.getProductId();
            int qtyToRefund = itemRequest.getQuantityRefunded();

            // Validate the product was part of the original transaction
            TransactionItem originalItem = txItemsByProduct.get(productId);
            if (originalItem == null) {
                throw new RuntimeException("Product " + productId + " was not part of transaction " + request.getTransactionId());
            }

            // Validate refund quantity doesn't exceed original minus already refunded
            int alreadyRefunded = alreadyRefundedQty.getOrDefault(productId, 0);
            int remainingRefundable = originalItem.getQuantity() - alreadyRefunded;
            if (qtyToRefund > remainingRefundable) {
                throw new RuntimeException("Cannot refund " + qtyToRefund + " units of product " + productId
                        + ". Only " + remainingRefundable + " units are refundable (original: "
                        + originalItem.getQuantity() + ", already refunded: " + alreadyRefunded + ")");
            }

            // Use the original transaction's unit prices (preserves original exchange rate)
            BigDecimal refundAmountBase = originalItem.getUnitPriceBase()
                    .multiply(BigDecimal.valueOf(qtyToRefund))
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal refundAmountPayment = originalItem.getUnitPricePayment()
                    .multiply(BigDecimal.valueOf(qtyToRefund))
                    .setScale(2, RoundingMode.HALF_UP);

            RefundItem refundItem = new RefundItem();
            refundItem.setRefund(refund);
            refundItem.setProductId(productId);
            refundItem.setQuantityRefunded(qtyToRefund);
            refundItem.setRefundAmountBase(refundAmountBase);
            refundItem.setRefundAmountPayment(refundAmountPayment);
            refundItem.setCreatedAt(Instant.now());
            refund.getItems().add(refundItem);

            totalRefundPayment = totalRefundPayment.add(refundAmountPayment);
            storeId = originalItem.getStoreId();
        }

        refund.setStoreId(storeId);
        refund.setRefundAmountPayment(totalRefundPayment);
        refund = refundRepository.save(refund);

        return CreateRefundResponse.builder()
                .success(true)
                .refundId(refund.getId().toString())
                .transactionId(refund.getTransactionId())
                .storeId(refund.getStoreId())
                .refundAmountPayment(refund.getRefundAmountPayment())
                .paymentCurrency(refund.getPaymentCurrency())
                .status(refund.getStatus())
                .build();
    }

    @Transactional
    @Override
    public RefundApprovalResponse updateRefundStatus(UUID refundId, RefundStatusRequest request) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new RuntimeException("Refund not found: " + refundId));

        if (!"PENDING".equals(refund.getStatus())) {
            throw new RuntimeException("Refund " + refundId + " is already " + refund.getStatus());
        }

        refund.setStatus(request.getStatus());

        // If COMPLETED, restore inventory and record a DEBIT transaction
        if ("COMPLETED".equals(request.getStatus())) {
            // Restore inventory for each refund item
            for (RefundItem item : refund.getItems()) {
                Inventory inventory = inventoryRepository
                        .findByStoreIdAndProductId(refund.getStoreId(), item.getProductId())
                        .orElseThrow(() -> new RuntimeException(
                                "Inventory not found for store: " + refund.getStoreId()
                                        + ", product: " + item.getProductId()));

                inventory.setQuantity(inventory.getQuantity() + item.getQuantityRefunded());
                inventory.setUpdatedAt(Instant.now());
                inventoryRepository.save(inventory);
            }

            // Fetch original transaction to get userId and original item details
            Transaction originalTransaction = transactionRepository
                    .findById(UUID.fromString(refund.getTransactionId()))
                    .orElseThrow(() -> new RuntimeException("Original transaction not found: " + refund.getTransactionId()));

            List<TransactionItem> originalItems = transactionItemRepository
                    .findByTransactionId(originalTransaction.getId());
            Map<String, TransactionItem> originalItemsByProduct = originalItems.stream()
                    .collect(Collectors.toMap(TransactionItem::getProductId, item -> item));

            // Create a DEBIT transaction for the refund
            Transaction refundTransaction = new Transaction();
            refundTransaction.setUserId(originalTransaction.getUserId());
            refundTransaction.setTransactionType("DEBIT");
            refundTransaction.setPaymentCurrency(refund.getPaymentCurrency());
            refundTransaction.setTotalAmount(refund.getRefundAmountPayment());
            refundTransaction.setCreatedAt(Instant.now());
            refundTransaction = transactionRepository.save(refundTransaction);

            // Create transaction items for the DEBIT using original exchange rates
            Map<String, BigDecimal> storeTotals = new HashMap<>();
            Map<String, String> storeCurrencies = new HashMap<>();

            for (RefundItem refundItem : refund.getItems()) {
                TransactionItem originalItem = originalItemsByProduct.get(refundItem.getProductId());

                TransactionItem debitItem = new TransactionItem();
                debitItem.setTransaction(refundTransaction);
                debitItem.setProductId(refundItem.getProductId());
                debitItem.setStoreId(refund.getStoreId());
                debitItem.setQuantity(refundItem.getQuantityRefunded());
                debitItem.setUnitPriceBase(originalItem.getUnitPriceBase());
                debitItem.setBaseCurrency(originalItem.getBaseCurrency());
                debitItem.setConversionRate(originalItem.getConversionRate());
                debitItem.setUnitPricePayment(originalItem.getUnitPricePayment());
                debitItem.setTotalBase(refundItem.getRefundAmountBase());
                debitItem.setTotalPayment(refundItem.getRefundAmountPayment());
                debitItem.setCreatedAt(Instant.now());
                transactionItemRepository.save(debitItem);

                storeTotals.merge(refund.getStoreId(), refundItem.getRefundAmountBase(), BigDecimal::add);
                storeCurrencies.putIfAbsent(refund.getStoreId(), originalItem.getBaseCurrency());
            }

            // Create StoreTransaction entries for the DEBIT
            for (Map.Entry<String, BigDecimal> entry : storeTotals.entrySet()) {
                StoreTransaction storeTransaction = new StoreTransaction();
                storeTransaction.setStoreId(entry.getKey());
                storeTransaction.setTransactionId(refundTransaction.getId().toString());
                storeTransaction.setTransactionType("DEBIT");
                storeTransaction.setTotalInBaseCurrency(entry.getValue());
                storeTransaction.setBaseCurrency(storeCurrencies.get(entry.getKey()));
                storeTransaction.setStatus("COMPLETED");
                storeTransaction.setCreatedAt(Instant.now());
                storeTransactionRepository.save(storeTransaction);
            }
        }

        refundRepository.save(refund);

        return RefundApprovalResponse.builder()
                .refundId(refund.getId().toString())
                .transactionId(refund.getTransactionId())
                .storeId(refund.getStoreId())
                .refundAmountPayment(refund.getRefundAmountPayment())
                .paymentCurrency(refund.getPaymentCurrency())
                .status(refund.getStatus())
                .build();
    }

    @Override
    public List<CreateRefundResponse> getRefundsByTransaction(String transactionId) {
        List<Refund> refunds = refundRepository.findByTransactionId(transactionId);

        return refunds.stream()
                .map(refund -> CreateRefundResponse.builder()
                        .success(true)
                        .refundId(refund.getId().toString())
                        .transactionId(refund.getTransactionId())
                        .storeId(refund.getStoreId())
                        .refundAmountPayment(refund.getRefundAmountPayment())
                        .paymentCurrency(refund.getPaymentCurrency())
                        .status(refund.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
