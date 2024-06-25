package com.upc.smartsproutbackend.dto;


import com.upc.smartsproutbackend.models.IrrigationRecord;
import com.upc.smartsproutbackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CropFieldDto {
    private Long cropFieldId;
    private String cropFieldName;
    private String cropFieldDescription;
    private double latitudeData;
    private double longitudeData;
    private double cropFieldSize;
    private String soilType;
    private String cropType;
    private String cropVariety;
    private String cropPlant;
    private LocalDate cropPlantingDate;
    private LocalTime irrigationStartTime;
    private Long numPlants;
    private double idealTemperature;
    private double idealHumidity;
    private List<IrrigationRecord> irrigationRecords = new ArrayList<>();
}
