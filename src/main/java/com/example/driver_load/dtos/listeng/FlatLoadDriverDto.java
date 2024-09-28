package com.example.driver_load.dtos.listeng;

import com.example.driver_load.enums.LoadStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class FlatLoadDriverDto {
    @Column("id")
    private Long id;
    @Column("loadNumber")
    private String loadNumber;
    @Column("pickUpAddress")
    private String pickUpAddress;
    @Column("deliveryAddress")
    private String deliveryAddress;
    @Column("status")
    private LoadStatus status;
    @Column("driverId")
    private Long driverId;
    @Column("driverName")
    private String driverName;
    @Column("driverSurname")
    private String driverSurname;
    @Column("driverEmail")
    private String driverEmail;
    @Column("driverPhone")
    private String driverPhone;

}