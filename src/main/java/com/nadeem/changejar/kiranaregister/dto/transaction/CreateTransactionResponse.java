package com.nadeem.changejar.kiranaregister.dto.transaction;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateTransactionResponse {
    private boolean success;
    private String transactionId;
    private String userId;
    private String transactionType;
    private String paymentCurrency;
    private BigDecimal totalAmount;
}
