package com.example.driver_load.repositories;

import com.example.driver_load.entities.Driver;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


public interface DriverRepository extends ReactiveCrudRepository<Driver,Long> {




}
