package com.upc.smartsproutbackend.repository;

import com.upc.smartsproutbackend.models.IrrigationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationSuggestionRepository extends JpaRepository<IrrigationSuggestion, Long> {
    IrrigationSuggestion findByCropFieldId(Long cropFieldId);
}
