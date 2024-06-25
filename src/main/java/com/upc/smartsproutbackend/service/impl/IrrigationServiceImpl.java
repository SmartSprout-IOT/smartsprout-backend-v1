package com.upc.smartsproutbackend.service.impl;

import com.upc.smartsproutbackend.models.CropField;
import com.upc.smartsproutbackend.models.IrrigationRecord;
import com.upc.smartsproutbackend.models.IrrigationSuggestion;
import com.upc.smartsproutbackend.models.TempDataSensor;
import com.upc.smartsproutbackend.repository.CropFieldRepository;
import com.upc.smartsproutbackend.repository.IrrigationRecordRepository;
import com.upc.smartsproutbackend.repository.TempDataSensorRepository;
import com.upc.smartsproutbackend.service.IoTSensorService;
import com.upc.smartsproutbackend.service.IrrigationRecordService;
import com.upc.smartsproutbackend.service.IrrigationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class IrrigationServiceImpl implements IrrigationService {
    private final CropFieldRepository cropFieldRepository;
    private final TempDataSensorRepository tempDataSensorRepository;
    private final IrrigationRecordRepository irrigationRecordRepository;

    public IrrigationServiceImpl(CropFieldRepository cropFieldRepository, TempDataSensorRepository tempDataSensorRepository, IrrigationRecordRepository irrigationRecordRepository) {
        this.cropFieldRepository = cropFieldRepository;
        this.tempDataSensorRepository = tempDataSensorRepository;
        this.irrigationRecordRepository = irrigationRecordRepository;
    }

    @Override
    public void startIrrigation(Long cropFieldId) {
        CropField cropField = cropFieldRepository.findById(cropFieldId).orElse(null);
        if (cropField != null) {
            //Temas de pruebas considerar cambiar a findById
            TempDataSensor tempDataSensor = tempDataSensorRepository.findByUserId(cropField.getUser().getId());
            if (tempDataSensor != null) {
                tempDataSensor.setCropFieldId(cropFieldId);
                tempDataSensor.setIrrigation(true);
                tempDataSensorRepository.save(tempDataSensor);
            }
            IrrigationSuggestion irrigationSuggestion = cropField.getIrrigationSuggestion();
            if (irrigationSuggestion != null) {
                irrigationSuggestion.setIrrigation(true);
            }
        }
        assert cropField != null;
        LocalTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalTime();
        cropField.setIrrigationStartTime(limaTime);
        cropFieldRepository.save(cropField);
    }

    @Override
    public IrrigationRecord completeIrrigationRecord(Long cropFieldId) {
        CropField cropField = cropFieldRepository.findById(cropFieldId).orElse(null);
        if (cropField != null) {
            TempDataSensor tempDataSensor = tempDataSensorRepository.findByUserId(cropField.getUser().getId());
            if (tempDataSensor != null) {
                tempDataSensor.setCropFieldId(0L);
                tempDataSensor.setIrrigation(false);
                tempDataSensorRepository.save(tempDataSensor);
            }
            IrrigationSuggestion irrigationSuggestion = cropField.getIrrigationSuggestion();
            if (irrigationSuggestion != null) {
                irrigationSuggestion.setIrrigation(false);
            }
            LocalTime limaTime = ZonedDateTime.now(ZoneId.of("America/Lima")).toLocalTime();
            cropField.setIrrigationEndTime(limaTime);
            cropFieldRepository.save(cropField);
            IrrigationRecord irrigationRecord = IrrigationRecord.builder()
                    .cropField(cropField)
                    .irrigationDate(LocalDate.now())
                    .startTime(cropField.getIrrigationStartTime())
                    .endTime(limaTime)
                    .duration((int) cropField.getIrrigationStartTime().until(cropField.getIrrigationEndTime(), ChronoUnit.MINUTES))
                    .build();
            cropField.setIrrigationStartTime(null);
            cropField.setIrrigationEndTime(null);
            return irrigationRecordRepository.save(irrigationRecord);
        }
        return null;
    }
}
