package com.upc.smartsproutbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateIrrigationRequest {
    private Long irrigationDuration;
    private LocalTime irrigationEndTime;
    private Boolean irrigationCompleted;
}
