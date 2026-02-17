package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionRequest;
import com.nadeem.changejar.kiranaregister.dto.transaction.CreateTransactionResponse;
import com.nadeem.changejar.kiranaregister.dto.transaction.TransactionItemRequest;
import com.nadeem.changejar.kiranaregister.entity.*;
import com.nadeem.changejar.kiranaregister.repository.*;
import com.nadeem.changejar.kiranaregister.service.auth.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final StoreTransactionRepository storeTransactionRepository;
    private final InventoryRepository inventoryRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final CurrencyService currencyService;

    @Transactional
    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        // 1. Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setPaymentCurrency(request.getPaymentCurrency());
        transaction.setTotalAmount(BigDecimal.ZERO);
        transaction.setCreatedAt(Instant.now());
        transaction = transactionRepository.save(transaction);

        BigDecimal totalPaymentAmount = BigDecimal.ZERO;

        // Aggregate store-level totals: storeId -> {totalInBase, baseCurrency}
        Map<String, BigDecimal> storeTotals = new HashMap<>();
        Map<String, String> storeCurrencies = new HashMap<>();

        // 2. Process each item
        for (TransactionItemRequest itemRequest : request.getItems()) {
            String productId = itemRequest.getProductId();
            String storeId = itemRequest.getStoreId();
            int quantity = itemRequest.getQuantity();

            // Validate inventory
            Inventory inventory = inventoryRepository.findByStoreIdAndProductId(storeId, productId)
                    .orElseThrow(() -> new RuntimeException("Inventory not found for store: " + storeId + ", product: " + productId));

            if (inventory.getQuantity() < quantity) {
                throw new RuntimeException("Insufficient inventory for product: " + productId + " in store: " + storeId);
            }

            // Fetch product price from MongoDB
            //Could also user optional to store from repo as well but orElseThrow is convenient.
            Product product = productRepository.findById(new ObjectId(productId))
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            // Fetch store to get base currency
            Store store = storeRepository.findById(new ObjectId(storeId))
                    .orElseThrow(() -> new RuntimeException("Store not found: " + storeId));

            String baseCurrency = store.getBaseCurrency();
            BigDecimal unitPriceBase = product.getDisplayPrice();

            BigDecimal conversionRate = currencyService.getRate(baseCurrency, request.getPaymentCurrency());

            BigDecimal unitPricePayment = unitPriceBase.multiply(conversionRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalBase = unitPriceBase.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal totalPayment = unitPricePayment.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_UP);

            // 3. Save transaction item
            TransactionItem item = new TransactionItem();
            item.setTransaction(transaction);
            item.setProductId(productId);
            item.setStoreId(storeId);
            item.setQuantity(quantity);
            item.setUnitPriceBase(unitPriceBase);
            item.setBaseCurrency(baseCurrency);
            item.setConversionRate(conversionRate);
            item.setUnitPricePayment(unitPricePayment);
            item.setTotalBase(totalBase);
            item.setTotalPayment(totalPayment);
            item.setCreatedAt(Instant.now());
            transactionItemRepository.save(item);

            // 4. Update inventory
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventory.setUpdatedAt(Instant.now());
            inventoryRepository.save(inventory);

            // 5. Aggregate totals per store
            storeTotals.merge(storeId, totalBase, BigDecimal::add);
            storeCurrencies.putIfAbsent(storeId, baseCurrency);

            totalPaymentAmount = totalPaymentAmount.add(totalPayment);
        }

        // 6. Create one StoreTransaction per store
        for (Map.Entry<String, BigDecimal> entry : storeTotals.entrySet()) {
            StoreTransaction storeTransaction = new StoreTransaction();
            storeTransaction.setStoreId(entry.getKey());
            storeTransaction.setTransactionId(transaction.getId().toString());
            storeTransaction.setTransactionType(request.getTransactionType());
            storeTransaction.setTotalInBaseCurrency(entry.getValue());
            storeTransaction.setBaseCurrency(storeCurrencies.get(entry.getKey()));
            storeTransaction.setStatus("COMPLETED");
            storeTransaction.setCreatedAt(Instant.now());
            storeTransactionRepository.save(storeTransaction);
        }

        // 7. Update transaction total
        transaction.setTotalAmount(totalPaymentAmount);
        transactionRepository.save(transaction);

        return CreateTransactionResponse.builder()
                .success(true)
                .transactionId(transaction.getId().toString())
                .userId(userId)
                .transactionType(request.getTransactionType())
                .paymentCurrency(request.getPaymentCurrency())
                .totalAmount(totalPaymentAmount)
                .build();
    }

    @Override
    public Optional<Transaction> getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
