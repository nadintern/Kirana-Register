package com.nadeem.changejar.kiranaregister.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String productName;
    private BigDecimal displayPrice;
    private String category;
}
