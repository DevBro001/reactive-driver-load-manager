package com.example.driver_load.configs;

import com.example.driver_load.configs.security.JwtRequestFilter;
import com.example.driver_load.dtos.ErrorBodyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import reactor.core.publisher.Mono;


import java.time.LocalDateTime;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final String[] WHITE_LIST = new String[]{
                   "/auth/**",
                   "/error",
                   "/api-docs",
                   "/swagger-ui.html",
                   "/swagger-ui/**",
                   "/webjars/swagger-ui/index.html"
        };

    private final ObjectMapper objectMapper;
    private final ReactiveUserDetailsService userDetailsService;


    private final ServerAccessDeniedHandler accessDeniedHandler;

    private final ServerAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Lazy
    public SecurityConfig(ObjectMapper objectMapper, @Qualifier("customUserDetailsService") ReactiveUserDetailsService userDetailsService, ServerAccessDeniedHandler accessDeniedHandler, ServerAuthenticationEntryPoint authenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.authorizeExchange((auth)->{
                 auth
                    .pathMatchers(WHITE_LIST).permitAll()
                    .pathMatchers("/**").authenticated();
                }
        );
        http.exceptionHandling((handler)->{
            handler.accessDeniedHandler(accessDeniedHandler);
            handler.authenticationEntryPoint(authenticationEntryPoint);
        });

        http.addFilterBefore(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION);


        return http.build();
    }



    @Bean
    public ServerAccessDeniedHandler accessDeniedHandler(){
         return  (exchange, exception) -> {
             ServerHttpRequest request = exchange.getRequest();
             ServerHttpResponse response = exchange.getResponse();
             ErrorBodyDto bodyDto = new ErrorBodyDto(
                    HttpStatus.FORBIDDEN.value(),
                    request.getURI().toString(),
                    request.getPath().toString(),
                    exception.getClass().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
             response.setStatusCode(HttpStatus.FORBIDDEN);
             response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

             try {
                 byte[] bytes = objectMapper.writeValueAsBytes(bodyDto);
                 return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))) ;
             } catch (JsonProcessingException e) {
                 return Mono.error(e);
             }
        };
    }

    @Bean
    public ServerAuthenticationEntryPoint authenticationEntryPoint(){
        return (exchange, exception) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            ErrorBodyDto bodyDto = new ErrorBodyDto(
                    HttpStatus.UNAUTHORIZED.value(),
                    request.getURI().toString(),
                    request.getPath().toString(),
                    exception.getClass().toString(),
                    exception.getMessage(),
                    LocalDateTime.now());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            try {
                byte[] bytes = objectMapper.writeValueAsBytes(bodyDto);
                return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes))) ;
            } catch (JsonProcessingException e) {
                return Mono.error(e);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
