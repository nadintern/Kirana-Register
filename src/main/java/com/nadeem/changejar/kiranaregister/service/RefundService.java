package com.nadeem.changejar.kiranaregister.service;

import com.nadeem.changejar.kiranaregister.dto.refund.*;

import java.util.List;
import java.util.UUID;

public interface RefundService {

    // create a refund request for a transaction
    CreateRefundResponse createRefund(CreateRefundRequest request);

    // approve or reject a refund
    RefundApprovalResponse updateRefundStatus(UUID refundId, RefundStatusRequest request);

    // get all refunds for a transaction
    List<CreateRefundResponse> getRefundsByTransaction(String transactionId);
}
