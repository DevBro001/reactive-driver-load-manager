package com.example.driver_load.dtos.create;

import com.example.driver_load.enums.LoadStatus;

public record LoadCreateDto(

        String loadNumber,
        String pickUpAddress,
        String deliveryAddress,
        long driverId,
        LoadStatus status,
        long userId
) {
}
