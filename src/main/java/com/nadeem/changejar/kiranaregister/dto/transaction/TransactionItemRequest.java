package com.nadeem.changejar.kiranaregister.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionItemRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Store ID is required")
    private String storeId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
