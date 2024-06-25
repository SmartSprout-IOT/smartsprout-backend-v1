package com.upc.smartsproutbackend.repository;

import com.upc.smartsproutbackend.models.TempDataSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempDataSensorRepository extends JpaRepository<TempDataSensor, Long> {
    TempDataSensor findByUserId(Long userId);
}
