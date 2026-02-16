package com.nadeem.changejar.kiranaregister.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String productName;
    private String category;
    private BigDecimal displayPrice;
    private int quantity;
}
