package com.nadeem.changejar.kiranaregister.dto.transaction;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateTransactionRequest {

    @NotBlank(message = "Payment currency is required")
    @Size(min = 3, max = 3, message = "Payment currency must be exactly 3 characters (e.g. USD, INR, EUR)")
    private String paymentCurrency;

    @NotBlank(message = "Transaction type is required")
    @Pattern(regexp = "CREDIT|DEBIT", message = "Transaction type must be 'CREDIT' or 'DEBIT'")
    private String transactionType;

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<TransactionItemRequest> items;
}
