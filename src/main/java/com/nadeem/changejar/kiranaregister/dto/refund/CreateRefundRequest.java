package com.nadeem.changejar.kiranaregister.dto.refund;

import lombok.Data;

import java.util.List;

@Data
public class CreateRefundRequest {
    private String transactionId;
    private String reason;
    private List<RefundItemRequest> items;
}
