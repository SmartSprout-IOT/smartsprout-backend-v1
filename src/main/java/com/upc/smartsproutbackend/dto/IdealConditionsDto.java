package com.upc.smartsproutbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdealConditionsDto {
    private double idealTemperature;
    private double idealHumidity;
}
