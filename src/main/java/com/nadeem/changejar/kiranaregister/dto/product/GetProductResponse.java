package com.nadeem.changejar.kiranaregister.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GetProductResponse {
    private String productId;
    private String productName;
    private String category;
    private BigDecimal displayPrice;
    private String storesCurrency;
    private boolean isActive;
    private int quantity;
}
