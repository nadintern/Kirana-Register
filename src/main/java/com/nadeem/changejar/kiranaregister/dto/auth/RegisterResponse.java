package com.nadeem.changejar.kiranaregister.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class RegisterResponse {
    private String message;
    private String userId;
    private String username;
    private List<String> roles;
    private Instant createdAt;
}
