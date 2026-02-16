package com.nadeem.changejar.kiranaregister.dto.transaction;

import lombok.Data;

import java.util.List;

@Data
public class CreateTransactionRequest {
    private String paymentCurrency;
    private String transactionType;
    private List<TransactionItemRequest> items;
}
