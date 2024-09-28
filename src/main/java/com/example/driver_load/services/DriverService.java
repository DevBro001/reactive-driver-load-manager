package com.example.driver_load.services;

import com.example.driver_load.dtos.create.DriverCreateDto;
import com.example.driver_load.dtos.update.DriverUpdateDto;
import com.example.driver_load.entities.Driver;
import com.example.driver_load.repositories.DriverRepository;
import com.example.driver_load.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.example.driver_load.utils.MonoErrors.driverNotFound;
import static com.example.driver_load.utils.MonoErrors.userNotFound;

@Service
@RequiredArgsConstructor
public class DriverService {

     private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public Mono<Driver> save(DriverCreateDto driverDto){
         return userRepository.findById(driverDto.userId())
                 .switchIfEmpty(userNotFound)
                 .flatMap(user-> driverRepository.save(
                             Driver.builder()
                                     .name(driverDto.name())
                                     .surname(driverDto.surname())
                                     .email(driverDto.email())
                                     .phone(driverDto.phone())
                                     .userId(user.getId())
                                     .build()
                     ))
                 ;
     }
    public Mono<Driver> get(Long id){
         return driverRepository.findById(id);
     }
    public Mono<Driver> update(DriverUpdateDto driverDto){
             return userRepository.findById(driverDto.userId())
                     .switchIfEmpty(userNotFound)
                     .flatMap(user -> driverRepository.findById(driverDto.id()))
                     .switchIfEmpty(driverNotFound)
                     .flatMap(driver ->{
                         driver.setEmail(driverDto.email());
                         driver.setName(driverDto.name());
                         driver.setPhone(driverDto.phone());
                         driver.setUserId(driverDto.userId());
                         driver.setSurname(driverDto.surname());
                         return driverRepository.save(driver);
                     });
    }

    public Mono<Void> delete(Long id){
        return driverRepository.findById(id)
                .switchIfEmpty(driverNotFound)
                .flatMap(driverRepository::delete);
    }

}
