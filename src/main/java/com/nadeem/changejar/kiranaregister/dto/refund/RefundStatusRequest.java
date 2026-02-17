package com.nadeem.changejar.kiranaregister.dto.refund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RefundStatusRequest {

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "COMPLETED|REJECTED", message = "Status must be 'COMPLETED' or 'REJECTED'")
    private String status;
}
