package com.upc.smartsproutbackend.service;

import com.upc.smartsproutbackend.models.IrrigationRecord;

public interface IrrigationService {
    public abstract void startIrrigation(Long cropFieldId);
    public abstract IrrigationRecord completeIrrigationRecord(Long cropFieldId);
}
