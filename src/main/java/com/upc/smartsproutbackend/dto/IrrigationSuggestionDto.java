package com.upc.smartsproutbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrrigationSuggestionDto {
    private double actualTemperature;
    private double actualHumidity;
    private boolean isIrrigation;
    private Long cropFieldId;
}
