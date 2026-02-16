package com.nadeem.changejar.kiranaregister.dto.store;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateStoreResponse {
    private boolean success;
    private String storeId;
    private String storeName;
    private String region;
    private String address;
    private String baseCurrency;
    private Instant createdAt;
    private Instant updatedAt;
}
