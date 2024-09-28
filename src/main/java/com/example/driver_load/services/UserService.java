package com.example.driver_load.services;

import com.example.driver_load.dtos.create.UserCreateDto;
import com.example.driver_load.entities.User;
import com.example.driver_load.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> save(UserCreateDto userDto) {
        String encode = passwordEncoder
                .encode(userDto.password());
        return userRepository.save(User.builder()
                .email(userDto.email())
                .name(userDto.name())
                .surname(userDto.surname())
                .username(userDto.username())
                .password(encode)
                .build());
    }
}
