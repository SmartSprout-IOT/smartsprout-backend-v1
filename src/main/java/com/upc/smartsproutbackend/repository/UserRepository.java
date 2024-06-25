package com.upc.smartsproutbackend.repository;

import com.upc.smartsproutbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUserEmailAndUserPassword(String user_email, String user_password);
    boolean existsById(Long user_id);
    boolean existsByUserEmail(String user_email);
    User findByUserEmail(String email);
    List<User>findAll();
}
