package com.nadeem.changejar.kiranaregister.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class UpdateProductResponse {
    private boolean success;
    private String productId;
    private String storeId;
    private String productName;
    private BigDecimal displayPrice;
    private String storesCurrency;
    private boolean isActive;
    private int currentStock;
    private Instant updatedAt;
}
