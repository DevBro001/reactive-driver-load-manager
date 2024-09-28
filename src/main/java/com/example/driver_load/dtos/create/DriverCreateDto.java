package com.example.driver_load.dtos.create;


public record DriverCreateDto(
        String name,
        String surname,
        String email,
        String phone,
        Long userId
) {
}
