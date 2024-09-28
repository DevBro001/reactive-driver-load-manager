package com.example.driver_load.dtos;

public record AuthenticationDto(
        String accessToken,
        String refreshToken
) {
}
