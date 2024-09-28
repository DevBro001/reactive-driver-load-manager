package com.example.driver_load.controllers;

import com.example.driver_load.configs.security.CustomUserDetailsService;
import com.example.driver_load.configs.security.LoginAuthenticationManager;
import com.example.driver_load.dtos.AuthenticationDto;
import com.example.driver_load.dtos.RefreshTokenDto;
import com.example.driver_load.dtos.create.UserCreateDto;
import com.example.driver_load.entities.User;
import com.example.driver_load.repositories.UserRepository;
import com.example.driver_load.services.UserService;
import com.example.driver_load.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final LoginAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody UserCreateDto user){
        return userService.save(user)
                .then(Mono.just(ResponseEntity.ok("User Successfully registered")));
    }
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthenticationDto>> authentication(@RequestParam("username")String username, @RequestParam("password")String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken)
                .map(authentication -> {
                    String accessToken = jwtUtil.generateAccessToken(username);
                    String refreshToken = jwtUtil.generateRefreshToken(username);
                    return ResponseEntity.ok(new AuthenticationDto(accessToken, refreshToken));
                });
    }

    @PostMapping("/refresh-access-token")
    public Mono<ResponseEntity<AuthenticationDto>> refreshAccessToken(@RequestBody RefreshTokenDto refreshTokenDto){
        return Mono.justOrEmpty(refreshTokenDto.refreshToken())
                .flatMap(refreshToken->{
                    String username = jwtUtil.getSubject(refreshToken);
                    return  userDetailsService.findByUsername(username);
                })
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(userDetails -> {
                    String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
                    String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
                    return ResponseEntity.ok(new AuthenticationDto(accessToken, refreshToken));
                });
    }



}
