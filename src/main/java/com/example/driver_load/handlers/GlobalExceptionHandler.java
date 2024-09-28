package com.example.driver_load.handlers;

import com.example.driver_load.dtos.ErrorBodyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseStatusExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ErrorBodyDto> handleResourceNotFoundException(Exception ex, ServerWebExchange exchange) {
        ex.printStackTrace();
        ServerHttpRequest request = exchange.getRequest();
        return Mono.just(   new ErrorBodyDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getURI().toString(),
                request.getPath().toString(),
                ex.getClass().toString(),
                ex.getMessage(),
                LocalDateTime.now()));
    }

}