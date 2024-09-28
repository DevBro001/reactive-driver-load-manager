package com.example.driver_load.dtos.update;

public record UserUpdateDto(
        long id,
        String username,
        String email,
        String name,
        String surname
) {
}
