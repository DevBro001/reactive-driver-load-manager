package com.example.driver_load.services;

import com.example.driver_load.dtos.create.LoadCreateDto;
import com.example.driver_load.dtos.listeng.LoadListingDto;
import com.example.driver_load.dtos.update.LoadUpdateDto;
import com.example.driver_load.entities.Load;
import com.example.driver_load.enums.LoadStatus;
import com.example.driver_load.repositories.DriverRepository;
import com.example.driver_load.repositories.LoadRepository;
import com.example.driver_load.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.driver_load.utils.MonoErrors.*;

@Service
@RequiredArgsConstructor
public class LoadService {
    private final LoadRepository loadRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public Mono<Load> save(LoadCreateDto loadDto) {
        Load load = Load.builder()
                .loadNumber(loadDto.loadNumber())
                .deliveryAddress(loadDto.deliveryAddress())
                .pickUpAddress(loadDto.pickUpAddress())
                .status(loadDto.status())
                .build();
        return userRepository.findById(loadDto.userId())
                .switchIfEmpty(userNotFound)
                .flatMap(user->{
                    load.setUserId(user.getId());
                    return driverRepository.findById(loadDto.driverId());
                })
                .switchIfEmpty(driverNotFound)
                .flatMap(driver -> {
                    load.setDriverId(driver.getId());
                    return loadRepository.save(load);
                });
    }
    public Mono<Load> get(Long id){
        return loadRepository.findById(id)
                .switchIfEmpty(loadNotFound);
    }

    public Mono<Load> update(LoadUpdateDto loadDto) {
        return userRepository.findById(loadDto.userId())
                .switchIfEmpty(userNotFound)
                .flatMap(user -> driverRepository.findById(loadDto.driverId()))
                .switchIfEmpty(driverNotFound)
                .flatMap(driver -> loadRepository.findById(loadDto.id()))
                .switchIfEmpty(loadNotFound)
                .flatMap(load -> {
                    load.setLoadNumber(loadDto.loadNumber());
                    load.setDeliveryAddress(loadDto.deliveryAddress());
                    load.setPickUpAddress(loadDto.pickUpAddress());
                    load.setStatus(loadDto.status());
                    return loadRepository.save(load);
                });
    }

    public Mono<Void> delete(Long id) {
        return loadRepository.findById(id)
                .switchIfEmpty(loadNotFound)
                .flatMap(loadRepository::delete);
    }

    public Flux<LoadListingDto> findAllForListing(int page, int count) {
        PageRequest pageRequest = PageRequest.of(page, count);
        return loadRepository.findAllLoadsForListing(pageRequest)
                .map(flatDto -> LoadListingDto.builder()
                        .id(flatDto.getId())
                        .loadNumber(flatDto.getLoadNumber())
                        .pickUpAddress(flatDto.getPickUpAddress())
                        .deliveryAddress(flatDto.getDeliveryAddress())
                        .status(flatDto.getStatus())
                        .driverId(flatDto.getDriverId())
                        .driverName(flatDto.getDriverName())
                        .driverSurname(flatDto.getDriverSurname())
                        .driverEmail(flatDto.getDriverEmail())
                        .driverPhone(flatDto.getDriverPhone())
                        .build());
    }
}
