package com.nadeem.changejar.kiranaregister.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductResponse {
    private boolean success;
    private String productId;
    private String storeId;
    private String productName;
    private String category;
    private BigDecimal displayPrice;
    private String storesCurrency;
}
