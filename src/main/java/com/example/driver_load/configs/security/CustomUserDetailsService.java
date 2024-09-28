package com.example.driver_load.configs.security;

import com.example.driver_load.configs.security.AuthUser;
import com.example.driver_load.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .switchIfEmpty(Mono.error(new BadCredentialsException("Username or password incorrect")))
                .map(user -> AuthUser.builder()
                        .id(user.getId())
                        .username(username)
                        .password(user.getPassword())
                        .build());
    }
}
