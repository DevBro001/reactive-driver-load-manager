package com.example.driver_load;

import com.example.driver_load.configs.security.AuthUser;
import com.example.driver_load.entities.User;
import com.example.driver_load.repositories.UserRepository;
import com.example.driver_load.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import reactor.core.publisher.Mono;

import java.util.Random;

@SpringBootApplication
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class DriverLoadApplication {

	private final UserRepository userRepository;
	private final SecurityUtils securityUtils;

	public static void main(String[] args) {
		SpringApplication.run(DriverLoadApplication.class, args);
	}


	@Bean
	public ReactiveAuditorAware<Long> myAuditorProvider() {
		return ()->securityUtils.getUser()
				.map(AuthUser::getId);
	}
}
