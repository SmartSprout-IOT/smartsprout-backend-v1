package com.upc.smartsproutbackend.controller;

import com.upc.smartsproutbackend.dto.IdealConditionsDto;
import com.upc.smartsproutbackend.dto.IrrigationSuggestionDto;
import com.upc.smartsproutbackend.dto.ManageConfigurationSensorDto;
import com.upc.smartsproutbackend.models.IrrigationRecord;
import com.upc.smartsproutbackend.service.IoTSensorService;
import com.upc.smartsproutbackend.service.IrrigationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/iot-management")
public class IoTSensorController {
    private final IoTSensorService ioTSensorService;
    private final IrrigationService irrigationService;


    public IoTSensorController(IoTSensorService ioTSensorService, IrrigationService irrigationService) {
        this.ioTSensorService = ioTSensorService;
        this.irrigationService = irrigationService;
    }

    @GetMapping("/ideal-conditions/{cropFieldId}")
    public ResponseEntity<IdealConditionsDto> getIdealConditions(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(ioTSensorService.getIdealConditions(cropFieldId));
    }

    @GetMapping("/manage-configuration")
    public ResponseEntity<ManageConfigurationSensorDto> manageConfigurationSensor() {
        return ResponseEntity.ok(ioTSensorService.manageConfigurationSensor());
    }

    @PutMapping("/irrigation-suggestion")
    public ResponseEntity<IrrigationSuggestionDto> updateIrrigationSuggestion(@RequestBody IrrigationSuggestionDto irrigationSuggestionDto) {
        return ResponseEntity.ok(ioTSensorService.updateIrrigationSuggestion(irrigationSuggestionDto));
    }

    @PostMapping("/irrigation-complete/{cropFieldId}")
    public ResponseEntity<IrrigationRecord> completeIrrigationRecord(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(irrigationService.completeIrrigationRecord(cropFieldId));
    }
}
