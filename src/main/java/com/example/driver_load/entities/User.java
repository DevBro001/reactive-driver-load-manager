package com.example.driver_load.entities;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String password;

    @Builder

    public User(Long id, Long createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, Long updatedBy, String username, String email, String name, String surname, String password) {
        super(id, createdBy, createdAt, updatedAt, updatedBy);
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
}
