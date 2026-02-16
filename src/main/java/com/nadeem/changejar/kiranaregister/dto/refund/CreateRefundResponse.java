package com.nadeem.changejar.kiranaregister.dto.refund;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateRefundResponse {
    private boolean success;
    private String refundId;
    private String transactionId;
    private String storeId;
    private BigDecimal refundAmountPayment;
    private String paymentCurrency;
    private String status;
}
