package com.example.backend.payload.request;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
