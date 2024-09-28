package com.example.driver_load.repositories;

import com.example.driver_load.dtos.listeng.FlatLoadDriverDto;
import com.example.driver_load.dtos.listeng.LoadListingDto;
import com.example.driver_load.entities.Load;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LoadRepository extends ReactiveCrudRepository<Load,Long> {



    @Query("""
            SELECT l.id , l.load_number as loadNumber, l.pick_up_address as pickUpAddress, 
                        l.delivery_address as deliveryAddress, l.status as status, d.name as driverName, d.surname as driverSurname, 
                        d.email as driverEmail, d.phone as driverPhone ,d.id as driverId
                        FROM loads l
                        JOIN drivers d ON l.driver_id = d.id
                        """)
    Flux<FlatLoadDriverDto> findAllLoadsForListing(Pageable pageRequest);
}
