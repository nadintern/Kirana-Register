package com.nadeem.changejar.kiranaregister.dto.refund;

import lombok.Data;

@Data
public class RefundItemRequest {
    private String productId;
    private int quantityRefunded;
}
