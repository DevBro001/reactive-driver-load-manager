package com.example.driver_load.dtos;


import java.time.LocalDateTime;


public record ErrorBodyDto(
    Integer status,
    String uri,
    String url,
    String reason,
    String message,
    LocalDateTime timestamp
){

}
