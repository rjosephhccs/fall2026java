package com.inventory.user.controller;

import com.inventory.user.dto.*;

import com.inventory.user.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/register")	// temporary, used for setup
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody CreateUserRequest dto) {
        return authService.registerUser(dto);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>>  authenticate(
    		@RequestBody UserCredentials dto) {
    	
        Map<String, String> response = new HashMap<>();
        response.put("token", authService.authenticate(dto));

    	return ResponseEntity.ok(response);
    }
    
    @PostMapping("/validate")
    public String tokenCheck(String token) {
    	return jwtUtil.extractUsername(token);
    }
}