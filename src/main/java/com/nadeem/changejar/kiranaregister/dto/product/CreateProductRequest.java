package com.nadeem.changejar.kiranaregister.dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be at most 100 characters")
    private String productName;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;

    @NotNull(message = "Display price is required")
    @DecimalMin(value = "0.01", message = "Display price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Display price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal displayPrice;

    @Min(value = 0, message = "Quantity must be 0 or greater")
    private int quantity;
}
