package com.nadeem.changejar.kiranaregister.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStoreRequest {

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
