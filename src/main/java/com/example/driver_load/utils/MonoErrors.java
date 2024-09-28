package com.example.driver_load.utils;

import com.example.driver_load.entities.Driver;
import com.example.driver_load.entities.Load;
import com.example.driver_load.entities.User;
import reactor.core.publisher.Mono;

public interface MonoErrors {
    Mono<User> userNotFound = Mono.error(new Exception("User Not Found"));
    Mono<Driver> driverNotFound = Mono.error(new Exception("Driver Not Found"));
    Mono<Load> loadNotFound = Mono.error(new Exception("Load Not Found"));
}
