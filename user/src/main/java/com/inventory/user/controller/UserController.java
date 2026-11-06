package com.inventory.user.controller;

import com.inventory.user.dto.*;
import com.inventory.user.entity.User;
import com.inventory.user.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(// added password hashing to store into the database
            @RequestBody CreateUserRequest dto) {
    	SecurityConfig hasher = new SecurityConfig();
    	String hashPass = hasher.passwordEncoder().encode(dto.passwordHash());
    	
    	CreateUserRequest updatedReq = new CreateUserRequest(dto.username(), hashPass, dto.role(), dto.firstName(), dto.lastName(), dto.email(), true);

        return userService.createUser(updatedReq);
    }
    
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody UserCredentials dto) {//verifying user credentials and generate token.
    	
    	User result  = userService.getUserByUsername(dto.username());
    	SecurityConfig checker = new SecurityConfig();
    	boolean valid = checker.passwordEncoder().matches(dto.password(), result.getPasswordHash());
    	System.out.println(valid);
    	
    	if(valid) {
        	String token = JwtUtil.generateToken(dto.username());
        	System.out.println(token);
    	}
    	
    	return null;
    }
    
    @PostMapping("/validate")
    public boolean tokenCheck(String token) {
    	boolean result = JwtUtil.validateToken(token);
    	System.out.println(result);
    	return result;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Integer id,
            @RequestBody UpdateUserRequest dto) {

        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}