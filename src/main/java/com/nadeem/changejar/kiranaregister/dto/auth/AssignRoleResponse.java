package com.nadeem.changejar.kiranaregister.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignRoleResponse {
    private String message;
    private String username;
    private String role;
}
