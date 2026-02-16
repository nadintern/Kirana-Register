package com.nadeem.changejar.kiranaregister.dto.transaction;

import lombok.Data;

@Data
public class TransactionItemRequest {
    private String productId;
    private String storeId;
    private int quantity;
}
