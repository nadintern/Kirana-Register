package com.nadeem.changejar.kiranaregister.dto.store;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreData {
    private String storeId;
    private String storeName;
    private String region;
    private String address;
    private String baseCurrency;
}
