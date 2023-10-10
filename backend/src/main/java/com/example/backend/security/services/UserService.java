package com.example.backend.security.services;

import com.example.backend.model.User;
import com.example.backend.payload.request.UpdateUserRequest;
import com.example.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequest userUpdates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        user.setUsername(userUpdates.getUsername());
        user.setEmail(userUpdates.getEmail());
        user.setFirstName(userUpdates.getFirstName());
        user.setLastName(userUpdates.getLastName());
        return user;
    }

    @Transactional
    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }
}

