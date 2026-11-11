package com.inventory.user.service;

import java.util.List;

import com.inventory.user.dto.*;
import com.inventory.user.entity.User;
import com.inventory.user.exception.UserNotFoundException;
import com.inventory.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder hasher;

    public UserService(UserRepository userRepository, PasswordEncoder hasher) {
        this.userRepository = userRepository;
    	this.hasher = hasher;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }
    
    /*
    public User getUserByUsername(String username) {
    	User user = userRepository.findByUsername(username).get();
    	
    	return user;
    }
    */

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return toDto(user);
    }

    public UserResponse createUser(CreateUserRequest dto) {

        User user = new User(
        		dto.username(),
                hasher.encode(dto.password()),
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
    
    /*
    public String authenticate(@RequestBody UserCredentials dto) {//verifying user credentials and generate token.
    	
    	User user = getUserByUsername(dto.username());
    	if (hasher.matches(dto.password(), user.getPassword()))
    		return jwtUtil.generateToken(dto.username());
    	else
    		throw new BadCredentialsException("Invalid username or password");
    }
    */

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