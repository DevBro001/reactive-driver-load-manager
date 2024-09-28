package com.example.driver_load.entities;


import com.example.driver_load.enums.LoadStatus;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "loads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Load extends BaseEntity {
    @Column("load_number")
    private String loadNumber;
    @Column("pick_up_address")
    private String pickUpAddress;
    @Column("delivery_address")
    private String deliveryAddress;
    @Column("driver_id")
    private Long driverId;
    private LoadStatus status;
    @Column("user_id")
    private Long userId;

    @Builder
    public Load(Long id, long createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, long updatedBy, String loadNumber, String pickUpAddress, String deliveryAddress, long driver, LoadStatus status, long user) {
        super(id, createdBy, createdAt, updatedAt, updatedBy);
        this.loadNumber = loadNumber;
        this.pickUpAddress = pickUpAddress;
        this.deliveryAddress = deliveryAddress;
        this.driverId = driver;
        this.status = status;
        this.userId = user;
    }
}

