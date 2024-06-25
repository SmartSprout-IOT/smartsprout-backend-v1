package com.upc.smartsproutbackend.service;

import com.upc.smartsproutbackend.dto.RegisterRequest;
import com.upc.smartsproutbackend.models.User;

import java.util.List;

public interface UserService {
    public abstract User createUser(User user);
    public void existsUserByEmail(User User);
    public abstract User getUserById(Long user_id);
    public abstract User updateUser(User user);
    public abstract void deleteUser(Long user_id);

    public abstract List<User> getAllUsers();
}
