package com.nadeem.changejar.kiranaregister.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    @Size(max = 100, message = "Product name must be at most 100 characters")
    private String productName;

    @DecimalMin(value = "0.01", message = "Display price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Display price must have at most 10 integer digits and 2 decimal places")
    private BigDecimal displayPrice;

    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;
}
