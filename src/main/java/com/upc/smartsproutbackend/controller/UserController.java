package com.upc.smartsproutbackend.controller;

import com.upc.smartsproutbackend.dto.UserDto;
import com.upc.smartsproutbackend.exception.ResourceNotFoundException;
import com.upc.smartsproutbackend.exception.ValidationException;
import com.upc.smartsproutbackend.models.User;
import com.upc.smartsproutbackend.repository.UserRepository;
import com.upc.smartsproutbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smartsprout/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // URL: http://localhost:8080/api/smartsprout/v1/users
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/users/{userId}
    // Method: GET
    @Transactional
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") Long userId) {
        existsUserByUserId(userId);
        User user = userService.getUserById(userId);
        UserDto userDto = convertToDto(user);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    // URL: http://localhost:8080/api/smartsprout/v1/users/{userId}
    // Method: DELETE
    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId) {
        existsUserByUserId(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    //PutController
    @Transactional
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "userId") Long userId, @RequestBody User user) {
        existsUserByUserId(userId);
        user.setId(userId);
        User updatedUser = ifDifferentOrEmptyUpdate(user);
        UserDto userDto = convertToDto(updatedUser);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }


    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserNames())
                .userLastName(user.getUserLastName())
                .userMotherLastName(user.getUserMotherLastName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userBirthDate(user.getUserBirthDate())
                .imageData(user.getImageData())
                .cropFields(user.getCropFields())
                .build();
    }

    private void existsUserByEmail(User user) {
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new ValidationException("Ya existe un usuario con el email " + user.getUserEmail());
        }
    }

    private void existsUserByEmail(String email) {
        if (!userRepository.existsByUserEmail(email)) {
            throw new ResourceNotFoundException("No existe un usuario con el email " + email);
        }
    }

    private void existsUserByUserId(Long userId) {
        if (userService.getUserById(userId) == null) {
            throw new ResourceNotFoundException("No existe un usuario con el id " + userId);
        }
    }

    private User ifDifferentOrEmptyUpdate(User user){
        return userRepository.findById(user.getId()).map(userToUpdate -> {
            if (user.getUserNames() != null && !user.getUserNames().isEmpty() && !user.getUserNames().equals(userToUpdate.getUserNames())) {
                userToUpdate.setUserNames(user.getUserNames());
            }
            if (user.getUserMotherLastName() != null && !user.getUserMotherLastName().isEmpty() && !user.getUserMotherLastName().equals(userToUpdate.getUserMotherLastName())) {
                userToUpdate.setUserMotherLastName(user.getUserMotherLastName());
            }

            if (user.getUserLastName() != null && !user.getUserLastName().isEmpty() && !user.getUserLastName().equals(userToUpdate.getUserLastName())) {
                userToUpdate.setUserLastName(user.getUserLastName());
            }
            if (user.getUserEmail() != null && !user.getUserEmail().isEmpty() && !user.getUserEmail().equals(userToUpdate.getUserEmail())) {
                userToUpdate.setUserEmail(user.getUserEmail());
            }
            /*if (user.getUserPassword() != null && !user.getUserPassword().isEmpty() && !user.getUserPassword().equals(userToUpdate.getUserPassword())) {
                userToUpdate.setUserPassword(user.getUserPassword());
            }*/
            if (user.getUserBirthDate() != null && !user.getUserBirthDate().equals(userToUpdate.getUserBirthDate())) {
                userToUpdate.setUserBirthDate(user.getUserBirthDate());
            }
            if (user.getUserPhone() != null && !user.getUserPhone().isEmpty() && !user.getUserPhone().equals(userToUpdate.getUserPhone())) {
                userToUpdate.setUserPhone(user.getUserPhone());
            }
            if (user.getImageData() != null && !user.getImageData().isEmpty() && !user.getImageData().equals(userToUpdate.getImageData())) {
                userToUpdate.setImageData(user.getImageData());
            }
            return userService.updateUser(userToUpdate);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + user.getId()));
    }
}
