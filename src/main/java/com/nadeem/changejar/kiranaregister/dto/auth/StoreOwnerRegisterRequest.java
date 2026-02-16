package com.nadeem.changejar.kiranaregister.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class StoreOwnerRegisterRequest {
    private String username;
    private String email;
    private String password;
    private List<StoreInfo> stores;

    @Data
    public static class StoreInfo {
        private String storeName;
        private String region;
        private String address;
        private String baseCurrency;
    }
}
