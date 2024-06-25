package com.upc.smartsproutbackend.controller;

import com.upc.smartsproutbackend.dto.CropFieldDto;
import com.upc.smartsproutbackend.exception.ResourceNotFoundException;
import com.upc.smartsproutbackend.models.CropField;
import com.upc.smartsproutbackend.repository.CropFieldRepository;
import com.upc.smartsproutbackend.service.CropFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/cropfield")
public class CropFieldController {
    @Autowired
    private CropFieldService cropFieldService;
    private final CropFieldRepository cropFieldRepository;

    public CropFieldController(CropFieldRepository cropFieldRepository) {
        this.cropFieldRepository = cropFieldRepository;
    }

    // URL: http://localhost:8080/api/smartsprout/v1/cropfields
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{cropFieldId}")
    public ResponseEntity<CropFieldDto> getCropFieldById(@PathVariable(name = "cropFieldId") Long cropFieldId) {
        existsCropFieldByCropFieldId(cropFieldId);
        CropField cropField = cropFieldService.getCropFieldById(cropFieldId);
        CropFieldDto cropFieldDto = convertToDto(cropField);
        return new ResponseEntity<CropFieldDto>(cropFieldDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/cropfield/{userId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CropFieldDto>> getCropFieldsByUserId(@PathVariable(name = "userId") Long userId) {
        List<CropField> cropFields = cropFieldService.getCropFieldsByUserId(userId);
        return new ResponseEntity<List<CropFieldDto>>(cropFields.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/cropfield/user/{userId}
    // Method: POST
    @Transactional
    @PostMapping("/user/{userId}")
    public ResponseEntity<CropFieldDto> createCropField(@PathVariable(name = "userId") Long userId, @RequestBody CropField cropFieldRequest) {
        CropField cropField = cropFieldService.createCropField(userId, cropFieldRequest);
        CropFieldDto cropFieldDtoResponse = convertToDto(cropField);
        return new ResponseEntity<CropFieldDto>(cropFieldDtoResponse, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/cropfields/{cropFieldId}
    // Method: PUT
    @Transactional
    @PutMapping("/{cropFieldId}")
    public ResponseEntity<CropFieldDto> updateCropField(@PathVariable(name = "cropFieldId") Long cropFieldId, @RequestBody CropField cropFieldRequest) {
        existsCropFieldByCropFieldId(cropFieldId);
        CropField cropField = cropFieldService.updateCropField(cropFieldId, cropFieldRequest);
        CropFieldDto cropFieldDtoResponse = convertToDto(cropField);
        return new ResponseEntity<CropFieldDto>(cropFieldDtoResponse, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/cropfields/{cropFieldId}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/{cropFieldId}")
    public ResponseEntity<Void> deleteCropField(@PathVariable(name = "cropFieldId") Long cropFieldId) {
        existsCropFieldByCropFieldId(cropFieldId);
        cropFieldService.deleteCropField(cropFieldId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private void existsCropFieldByCropFieldId(Long cropFieldId) {
        if (!cropFieldRepository.existsById(cropFieldId)) {
            throw new ResourceNotFoundException("CropField not found with id: " + cropFieldId);
        }
    }

    private CropFieldDto convertToDto(CropField cropField) {
        return CropFieldDto.builder()
                .cropFieldId(cropField.getId())
                .cropFieldName(cropField.getCropFieldName())
                .cropFieldDescription(cropField.getCropFieldDescription())
                .latitudeData(cropField.getLatitudeData())
                .longitudeData(cropField.getLongitudeData())
                .cropFieldSize(cropField.getCropFieldSize())
                .soilType(cropField.getSoilType())
                .cropType(cropField.getCropType())
                .cropVariety(cropField.getCropVariety())
                .cropPlant(cropField.getCropPlant())
                .cropPlantingDate(cropField.getCropPlantingDate())
                .irrigationStartTime(cropField.getIrrigationStartTime())
                .numPlants(cropField.getNumPlants())
                .idealTemperature(cropField.getIdealTemperature())
                .idealHumidity(cropField.getIdealHumidity())
                .irrigationRecords(cropField.getIrrigationRecords())
                .build();
    }

}
