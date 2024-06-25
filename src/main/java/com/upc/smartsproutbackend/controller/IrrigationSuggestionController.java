package com.upc.smartsproutbackend.controller;


import com.upc.smartsproutbackend.dto.IrrigationSuggestionDto;
import com.upc.smartsproutbackend.dto.IrrigationSuggestionResponse;
import com.upc.smartsproutbackend.service.IoTSensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/irrigation-suggestion")
public class IrrigationSuggestionController {
    private final IoTSensorService ioTSensorService;

    public IrrigationSuggestionController(IoTSensorService ioTSensorService) {
        this.ioTSensorService = ioTSensorService;
    }

    @GetMapping("/{cropFieldId}")
    public ResponseEntity<IrrigationSuggestionResponse> getIrrigationSuggestion(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(ioTSensorService.getIrrigationSuggestion(cropFieldId));
    }
}
