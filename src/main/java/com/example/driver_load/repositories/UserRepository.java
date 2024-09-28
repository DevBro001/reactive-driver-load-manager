package com.example.driver_load.repositories;

import com.example.driver_load.entities.Load;
import com.example.driver_load.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
    Mono<User> findUserByUsername(String username);
}
