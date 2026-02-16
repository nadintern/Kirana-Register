package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.refund.*;
import com.nadeem.changejar.kiranaregister.repository.RefundItemRepository;
import com.nadeem.changejar.kiranaregister.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final RefundItemRepository refundItemRepository;

    @Override
    public CreateRefundResponse createRefund(CreateRefundRequest request) {
        // TODO: implement - validate transaction, calculate refund amounts,
        //  save refund + refund_items
        return null;
    }

    @Override
    public RefundApprovalResponse updateRefundStatus(UUID refundId, RefundStatusRequest request) {
        // TODO: implement - update refund status, if COMPLETED restore inventory
        return null;
    }

    @Override
    public List<CreateRefundResponse> getRefundsByTransaction(String transactionId) {
        // TODO: implement - fetch refunds and map to response
        return List.of();
    }
}
