package com.upc.smartsproutbackend.repository;

import com.upc.smartsproutbackend.models.IrrigationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


public interface IrrigationRecordRepository extends JpaRepository<IrrigationRecord, Long> {
    List<IrrigationRecord> findByCropFieldId(Long cropFieldId);
    List<IrrigationRecord> findByIrrigationDateBetweenAndCropFieldId(LocalDate startDate, LocalDate endDate, Long cropFieldId);
}
