package com.nadeem.changejar.kiranaregister.dto.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class StoreOwnerRegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be 3-30 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address (e.g. user@example.com)")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be 6-100 characters")
    private String password;

    @NotEmpty(message = "At least one store is required")
    @Valid
    private List<StoreInfo> stores;

    @Data
    public static class StoreInfo {

        @NotBlank(message = "Store name is required")
        @Size(max = 100, message = "Store name must be at most 100 characters")
        private String storeName;

        @NotBlank(message = "Region is required")
        @Size(max = 50, message = "Region must be at most 50 characters")
        private String region;

        @NotBlank(message = "Address is required")
        @Size(max = 255, message = "Address must be at most 255 characters")
        private String address;

        @NotBlank(message = "Base currency is required")
        @Size(min = 3, max = 3, message = "Base currency must be exactly 3 characters (e.g. INR, USD, EUR)")
        private String baseCurrency;
    }
}
