package com.example.driver_load.entities;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Driver extends BaseEntity {

    private String name;
    private String surname;
    private String email;
    private String phone;
    @Column("user_id")
    private Long userId;
    @Builder

    public Driver(Long id, Long createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, Long updatedBy, String name, String surname, String email, String phone, Long userId) {
        super(id, createdBy, createdAt, updatedAt, updatedBy);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }
}
