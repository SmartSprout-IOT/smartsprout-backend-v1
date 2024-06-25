package com.upc.smartsproutbackend.service;

import com.upc.smartsproutbackend.models.IrrigationRecord;

import java.time.LocalDate;
import java.util.List;

public interface IrrigationRecordService {
    public abstract IrrigationRecord createIrrigationRecord(IrrigationRecord irrigationRecord, Long cropFieldId);
    public abstract void deleteIrrigationRecord(Long irrigationRecordId);
    public abstract IrrigationRecord getIrrigationRecordById(Long irrigationRecordId);
    public abstract List<IrrigationRecord> getAllIrrigationRecordsByCropFieldId(Long cropFieldId);
    public abstract List<IrrigationRecord> getIrrigationByBetweenDatesAndCropFieldId(LocalDate startDate, LocalDate endDate, Long cropFieldId);
}
