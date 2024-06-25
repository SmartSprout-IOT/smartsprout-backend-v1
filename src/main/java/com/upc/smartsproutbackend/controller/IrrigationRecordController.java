package com.upc.smartsproutbackend.controller;

import com.upc.smartsproutbackend.models.IrrigationRecord;
import com.upc.smartsproutbackend.service.IrrigationRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/irrigation-record")
public class IrrigationRecordController {
    private final IrrigationRecordService irrigationRecordService;

    public IrrigationRecordController(IrrigationRecordService irrigationRecordService) {
        this.irrigationRecordService = irrigationRecordService;
    }

    @GetMapping("/cropField/{cropFieldId}")
    public ResponseEntity<List<IrrigationRecord>> getIrrigationRecordsByCropFieldId(@PathVariable Long cropFieldId) {
        List<IrrigationRecord> records = irrigationRecordService.getAllIrrigationRecordsByCropFieldId(cropFieldId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/cropField/{cropFieldId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<List<IrrigationRecord>> getIrrigationRecordsByCropFieldIdAndDateRange(
            @PathVariable Long cropFieldId,
            @PathVariable String startDate,
            @PathVariable String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<IrrigationRecord> records = irrigationRecordService.getIrrigationByBetweenDatesAndCropFieldId(start, end, cropFieldId);
        return ResponseEntity.ok(records);
    }
}

