package com.example.driver_load.dtos.update;

import com.example.driver_load.enums.LoadStatus;

public record LoadUpdateDto(
        long id,
        String loadNumber,
        String pickUpAddress,
        String deliveryAddress,
        long driverId,
        LoadStatus status,
        long userId
) {
}
