package com.inventory.user.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.inventory.user.dto.CreateUserRequest;
import com.inventory.user.dto.UserCredentials;
import com.inventory.user.entity.User;
import com.inventory.user.repository.UserRepository;

@Service
public class AuthService {
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder hasher;
	
	public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder hasher) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.hasher = hasher;
	}
	
	//temporary, for setup
	public String registerUser(CreateUserRequest dto) {

        User user = new User(
        		dto.username(),
                hasher.encode(dto.password()),
                dto.role(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.isActive()
        );

        userRepository.save(user);

        return "User registered";
    }
	
	public String authenticate(@RequestBody UserCredentials dto) {//verifying user credentials and generate token.
    	User user = userRepository.findByUsername(dto.username())
    			.orElseThrow(() -> new BadCredentialsException("Invalid username"));
    	
    	if (hasher.matches(dto.password(), user.getPassword()))
    		return jwtUtil.generateToken(dto.username());
    	else
    		throw new BadCredentialsException("Invalid password");
    }
}
