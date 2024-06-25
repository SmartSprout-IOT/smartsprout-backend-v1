package com.upc.smartsproutbackend.dto;


import com.upc.smartsproutbackend.models.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String userName;
    private String userLastName;
    private String userMotherLastName;
    private String userEmail;
    private String userPassword;
    private String userPhone;
    private LocalDate userBirthDate;
    private String imageData;
    private Roles role;
}
