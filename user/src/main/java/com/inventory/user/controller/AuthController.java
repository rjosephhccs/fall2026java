package com.inventory.user.controller;

import com.inventory.user.dto.*;
import com.inventory.user.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
    
    @PostMapping("/authenticate")
    public String authenticate(
    		@RequestBody UserCredentials dto) {
    	return authService.authenticate(dto);
    }
    
    @PostMapping("/validate")
    public String tokenCheck(String token) {
    	return jwtUtil.extractUsername(token);
    }
}