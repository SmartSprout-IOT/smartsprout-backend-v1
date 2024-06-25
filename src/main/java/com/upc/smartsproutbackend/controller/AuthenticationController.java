package com.upc.smartsproutbackend.controller;

import com.upc.smartsproutbackend.dto.AuthenticationResponse;
import com.upc.smartsproutbackend.dto.LoginRequest;
import com.upc.smartsproutbackend.dto.RegisterRequest;
import com.upc.smartsproutbackend.models.CropField;
import com.upc.smartsproutbackend.repository.UserRepository;
import com.upc.smartsproutbackend.service.AuthService;
import com.upc.smartsproutbackend.service.CropFieldService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CropFieldService cropFieldService;

    // URL: http://localhost:8080/api/smartsprout/v1/auth/register
    // Method: POST
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerStudent(@RequestBody RegisterRequest request) {
        authService.existsUserByEmail(request);
        authService.validateRegisterRequest(request);
        AuthenticationResponse registeredUser = authService.register(request);
        return new ResponseEntity<AuthenticationResponse>(registeredUser, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/auth/login
    // Method: POST
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse loggedUser = authService.login(request);
        return new ResponseEntity<AuthenticationResponse>(loggedUser, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @Transactional(readOnly = true)
    @GetMapping("/cropfield/{userId}")
    public ResponseEntity<List<CropField>> getCropFieldsByUserId(@PathVariable(name = "userId") Long userId) {
        List<CropField> cropFields = cropFieldService.getCropFieldsByUserId(userId);
        return new ResponseEntity<List<CropField>>(cropFields, HttpStatus.OK);
    }
}
