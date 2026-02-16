package com.nadeem.changejar.kiranaregister.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private long expiresInSeconds;
}
