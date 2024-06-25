package com.upc.smartsproutbackend.service.impl;

import com.upc.smartsproutbackend.exception.ValidationException;
import com.upc.smartsproutbackend.models.TempDataSensor;
import com.upc.smartsproutbackend.models.User;
import com.upc.smartsproutbackend.repository.TempDataSensorRepository;
import com.upc.smartsproutbackend.repository.UserRepository;
import com.upc.smartsproutbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TempDataSensorRepository tempDataSensorRepository;

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void existsUserByEmail(User user) {
        if(userRepository.existsByUserEmail(user.getUserEmail())){
            throw new ValidationException("Ya existe un usuario con el email " + user.getUserEmail());
        }
    }

    @Override
    public User getUserById(Long user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
