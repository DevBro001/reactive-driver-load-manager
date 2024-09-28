package com.example.driver_load.controllers;

import com.example.driver_load.dtos.create.DriverCreateDto;
import com.example.driver_load.dtos.update.DriverUpdateDto;
import com.example.driver_load.entities.Driver;
import com.example.driver_load.services.DriverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class DriverController {
    private final DriverService driverService;

    @PostMapping("/create")
    public Mono<ResponseEntity<Driver>> create(@RequestBody DriverCreateDto driverDto){
        return driverService.save(driverDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/get/{id}")
    public Mono<ResponseEntity<Driver>> get(@PathVariable Long id){
        return driverService.get(id)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<Driver>> get(@RequestBody DriverUpdateDto driverDto){
        return driverService.update(driverDto)
                .map(ResponseEntity::ok);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable Long id){
        return driverService.delete(id)
                .then(Mono.just(ResponseEntity.ok("Driver Deleted Successfully")));
    }

}
