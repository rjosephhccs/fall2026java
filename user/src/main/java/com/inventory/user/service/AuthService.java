package com.inventory.user.service;

import com.inventory.user.dto.LoginRequest;
import com.inventory.user.dto.CreateUserRequest;
import com.inventory.user.entity.User;
import com.inventory.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(CreateUserRequest dto){
        User user=new User(
        		dto.username(),
                passwordEncoder.encode(dto.password()),
                dto.role(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.isActive()
        	);
        userRepository.save(user);
        return "user registered successfully";
    }

    public String login(LoginRequest request){
        User user=userRepository.findByUsername(request.username()).orElseThrow(()->new RuntimeException("user not found"));
        if(!passwordEncoder.matches(request.password(),user.getPassword())){
            throw new RuntimeException("invalid credentials");
        }

        return jwtService.generateToken(user.getUsername());
    }
}