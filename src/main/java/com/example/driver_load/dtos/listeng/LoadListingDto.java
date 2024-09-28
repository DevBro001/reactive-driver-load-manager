package com.example.driver_load.dtos.listeng;

import com.example.driver_load.enums.LoadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoadListingDto {

    private Long id;
    private String loadNumber;
    private String pickUpAddress;
    private String deliveryAddress;
    private LoadStatus status;


    private DriverListingDto driver;

    @Builder
    public LoadListingDto(Long id, String loadNumber, String pickUpAddress, String deliveryAddress, LoadStatus status, Long driverId,  String driverName, String driverSurname, String driverEmail, String driverPhone) {
        this.id = id;
        this.loadNumber = loadNumber;
        this.pickUpAddress = pickUpAddress;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.driver = new DriverListingDto( driverId,driverName, driverSurname, driverEmail, driverPhone);
    }

    @Getter
    @Setter
    static class DriverListingDto{
        private Long id;
        private String name;
        private String surname;
        private String email;
        private String phone;

        public DriverListingDto(Long id,String name, String surname, String email, String phone) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.phone = phone;
        }
    }
}


