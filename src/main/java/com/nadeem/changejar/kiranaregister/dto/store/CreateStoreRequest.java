package com.nadeem.changejar.kiranaregister.dto.store;

import lombok.Data;

@Data
public class CreateStoreRequest {
    private String storeName;
    private String region;
    private String address;
    private String baseCurrency;
}
