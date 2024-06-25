package com.upc.smartsproutbackend.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CardDto {
    private Long id;
    private String cardNumber;
    private String cardType;
    private String cardCvv;
    private LocalDate cardExpirationDate;
    private Double cardAmount;
    private String cardHolder;
    private boolean cardMain;
    private Long userId;
}
