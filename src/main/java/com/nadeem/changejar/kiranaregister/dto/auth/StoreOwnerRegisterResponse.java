package com.nadeem.changejar.kiranaregister.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StoreOwnerRegisterResponse {
    private String message;
    private String username;
    private String password;
    private String role;
    private List<StoreRoleInfo> storeRoles;

    @Data
    @Builder
    public static class StoreRoleInfo {
        private String storeId;
        private String role;
    }
}
