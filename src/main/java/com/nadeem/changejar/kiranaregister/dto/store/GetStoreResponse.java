package com.nadeem.changejar.kiranaregister.dto.store;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetStoreResponse {
    private boolean success;
    private List<StoreData> data;
}
