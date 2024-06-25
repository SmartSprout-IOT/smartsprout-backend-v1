package com.upc.smartsproutbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "crop_field")
public class CropField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String cropFieldName;
    @Column(name = "description", nullable = false, length = 50)
    private String cropFieldDescription;
    @Column(name = "latitude_data", nullable = false, length = 50)
    private double latitudeData;
    @Column(name = "longitude_data", nullable = false, length = 50)
    private double longitudeData;
    @Column(name = "size", nullable = false, length = 50)
    private double cropFieldSize;
    @Column(name = "soil_type", nullable = false, length = 50)
    private String soilType;
    @Column(name = "crop_type", nullable = false, length = 50)
    private String cropType;
    @Column(name = "crop_variety", nullable = false, length = 50)
    private String cropVariety;
    @Column(name = "crop_plant", nullable = false, length = 50)
    private String cropPlant;
    @Column(name = "planting_date", nullable = false, length = 50)
    private LocalDate cropPlantingDate;
    @Column(name = "num_plants", nullable = false)
    private Long numPlants;
    @Column(name = "ideal_temperature", nullable = false)
    private double idealTemperature;
    @Column(name = "ideal_humidity", nullable = false)
    private double idealHumidity;
    @Column(name = "irrigation", nullable = false)
    private boolean isIrrigation;


    @Column(name = "irrigation_duration")
    private Long irrigationDuration;
    @Column(name = "irrigation_starttime")
    private LocalDateTime irrigationStartTime;
    @Column(name = "irrigation_endtime")
    private LocalDateTime irrigationEndTime;
    @Column(name = "irrigation_completed")
    private boolean irrigationCompleted;

    @JsonIgnore
    @OneToMany(mappedBy = "cropField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IrrigationRecord> irrigationRecords;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_CROP_FIELD_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "cropField", cascade = CascadeType.ALL, orphanRemoval = true)
    private IrrigationSuggestion irrigationSuggestion;

}
