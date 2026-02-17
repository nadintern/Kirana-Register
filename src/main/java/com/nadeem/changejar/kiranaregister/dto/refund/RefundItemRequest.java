package com.nadeem.changejar.kiranaregister.dto.refund;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefundItemRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @Min(value = 1, message = "Quantity refunded must be at least 1")
    private int quantityRefunded;
}
