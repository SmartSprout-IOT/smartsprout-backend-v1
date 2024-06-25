package com.upc.smartsproutbackend.service.impl;

import com.upc.smartsproutbackend.dto.IdealConditionsDto;
import com.upc.smartsproutbackend.dto.IrrigationSuggestionDto;
import com.upc.smartsproutbackend.dto.IrrigationSuggestionResponse;
import com.upc.smartsproutbackend.dto.ManageConfigurationSensorDto;
import com.upc.smartsproutbackend.exception.ResourceNotFoundException;
import com.upc.smartsproutbackend.models.CropField;
import com.upc.smartsproutbackend.models.IrrigationSuggestion;
import com.upc.smartsproutbackend.models.TempDataSensor;
import com.upc.smartsproutbackend.repository.CropFieldRepository;
import com.upc.smartsproutbackend.repository.IrrigationSuggestionRepository;
import com.upc.smartsproutbackend.repository.TempDataSensorRepository;
import com.upc.smartsproutbackend.service.IoTSensorService;
import org.springframework.stereotype.Service;

@Service
public class IoTSensorServiceImpl implements IoTSensorService {
    private final CropFieldRepository cropFieldRepository;
    private final TempDataSensorRepository tempDataSensorRepository;
    private final IrrigationSuggestionRepository irrigationSuggestionRepository;

    public IoTSensorServiceImpl(CropFieldRepository cropFieldRepository, TempDataSensorRepository tempDataSensorRepository, IrrigationSuggestionRepository irrigationSuggestionRepository) {
        this.cropFieldRepository = cropFieldRepository;
        this.tempDataSensorRepository = tempDataSensorRepository;
        this.irrigationSuggestionRepository = irrigationSuggestionRepository;
    }

    @Override
    public IdealConditionsDto getIdealConditions(Long cropFieldId) {
        CropField cropField = cropFieldRepository.findById(cropFieldId).orElseThrow(() -> new ResourceNotFoundException("CropField not found"));
        return IdealConditionsDto.builder()
                .idealTemperature(cropField.getIdealTemperature())
                .idealHumidity(cropField.getIdealHumidity())
                .build();
    }

    @Override
    public ManageConfigurationSensorDto manageConfigurationSensor() {
        TempDataSensor tempDataSensor = tempDataSensorRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("TempDataSensor not found"));
        CropField cropField = cropFieldRepository.findById(tempDataSensor.getCropFieldId()).orElseThrow(() -> new ResourceNotFoundException("CropField not found"));
        cropField.setIrrigation(tempDataSensor.isIrrigation());
        cropFieldRepository.save(cropField);
        return ManageConfigurationSensorDto.builder()
                .cropFieldId(tempDataSensor.getCropFieldId())
                .isIrrigation(tempDataSensor.isIrrigation())
                .build();
    }

    @Override
    public IrrigationSuggestion saveIrrigationSuggestion(Long cropFieldId) {
        CropField cropField = cropFieldRepository.findById(cropFieldId).orElseThrow(() -> new ResourceNotFoundException("CropField not found"));
        IrrigationSuggestion irrigationSuggestion = IrrigationSuggestion.builder()
                .cropField(cropField)
                .actualHumidity(0)
                .actualTemperature(0)
                .isIrrigation(false)
                .build();
        return irrigationSuggestionRepository.save(irrigationSuggestion);
    }

    @Override
    public IrrigationSuggestionDto updateIrrigationSuggestion(IrrigationSuggestionDto irrigationSuggestionDto) {
        IrrigationSuggestion irrigationSuggestion = irrigationSuggestionRepository.findByCropFieldId(irrigationSuggestionDto.getCropFieldId());
        irrigationSuggestion.setActualHumidity(irrigationSuggestionDto.getActualHumidity());
        irrigationSuggestion.setActualTemperature(irrigationSuggestionDto.getActualTemperature());
        irrigationSuggestionRepository.save(irrigationSuggestion);
        return IrrigationSuggestionDto.builder()
                .cropFieldId(irrigationSuggestion.getCropField().getId())
                .actualHumidity(irrigationSuggestion.getActualHumidity())
                .actualTemperature(irrigationSuggestion.getActualTemperature())
                .isIrrigation(irrigationSuggestion.isIrrigation())
                .build();
    }

    @Override
    public IrrigationSuggestionResponse getIrrigationSuggestion(Long cropFieldId) {
        IrrigationSuggestion irrigationSuggestion = irrigationSuggestionRepository.findByCropFieldId(cropFieldId);
        return IrrigationSuggestionResponse.builder()
                .actualHumidity(irrigationSuggestion.getActualHumidity())
                .actualTemperature(irrigationSuggestion.getActualTemperature())
                .isIrrigation(irrigationSuggestion.isIrrigation())
                .build();
    }
}
