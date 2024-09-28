package com.example.driver_load.dtos.update;


public record DriverUpdateDto(
        Long id,
        String name,
        String surname,
        String email,
        String phone,
        Long userId
) {
}
