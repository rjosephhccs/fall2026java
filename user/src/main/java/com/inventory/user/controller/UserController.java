package com.inventory.user.controller;

import com.inventory.user.dto.*;
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
    public UserResponse createUser(// moved password hashing to UserService
            @RequestBody CreateUserRequest dto) {
        return userService.createUser(dto);
    }
    
    /*
    @PostMapping("/authenticate")
    public String authenticate(// moved authentication to UserService
    		@RequestBody UserCredentials dto) {
    	return userService.authenticate(dto);
    }
    
    @PostMapping("/validate")
    public String tokenCheck(String token) {
    	return jwtUtil.extractUsername(token);
    }
    */

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