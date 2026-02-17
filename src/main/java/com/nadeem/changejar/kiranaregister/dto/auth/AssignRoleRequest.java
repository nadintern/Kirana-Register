package com.nadeem.changejar.kiranaregister.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AssignRoleRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "ADMIN", message = "Role must be 'ADMIN'")
    private String role;
}
