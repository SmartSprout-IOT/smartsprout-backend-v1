package com.upc.smartsproutbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "irrigation_suggestion")
public class IrrigationSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actualTemperature" , nullable = false)
    private double actualTemperature;

    @Column(name = "actualHumidity" , nullable = false)
    private double actualHumidity;

    @Column(name = "isIrrigation" , nullable = false)
    private boolean isIrrigation;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_field_id", nullable = false)
    private CropField cropField;
}
