package com.nadeem.changejar.kiranaregister.dto.refund;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateRefundRequest {

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @Size(max = 500, message = "Reason must be at most 500 characters")
    private String reason;

    @NotEmpty(message = "At least one refund item is required")
    @Valid
    private List<RefundItemRequest> items;
}
