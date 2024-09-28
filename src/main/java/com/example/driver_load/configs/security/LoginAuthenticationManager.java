package com.example.driver_load.configs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginAuthenticationManager implements ReactiveAuthenticationManager {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        return customUserDetailsService.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User Not Found")))
                .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }

}
