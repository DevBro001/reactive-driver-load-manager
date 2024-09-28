package com.example.driver_load.dtos.create;

public record UserCreateDto(
        String username,
        String email,
        String name,
        String surname,
        String password
) {
}
