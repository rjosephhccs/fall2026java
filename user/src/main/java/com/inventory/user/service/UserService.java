package com.inventory.user.service;

import java.util.List;

import com.inventory.user.dto.*;
import com.inventory.user.entity.User;
import com.inventory.user.exception.UserNotFoundException;
import com.inventory.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return toDto(user);
    }

    public UserResponse createUser(CreateUserRequest dto) {

        User user = new User(
        		dto.username(),
                dto.passwordHash(),
                dto.role(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.isActive()
        );

        User saved = userRepository.save(user);

        return toDto(saved);
    }

    public UserResponse updateUser(Integer id, UpdateUserRequest dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setRole(dto.role());
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setActive(dto.isActive());

        User updated = userRepository.save(user);

        return toDto(updated);
    }
    
    // soft deletion
    public void deleteUser(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setActive(false);
        userRepository.save(user);
    }

    private UserResponse toDto(User user) {

        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}