package com.ims.users.service;

import com.ims.users.entity.User;

public class JwtService {
	public String generateToken(User user) {

	    return Jwt.builder()
	            .subject(user.getUsername())
	            .claim("role", user.getRole())
	            .claim("userId", user.getUserId())
	            .issuedAt(new Date())
	            .expiration(
	                  new Date(
	                     System.currentTimeMillis() +
	                     expirationMs))
	            .signWith(secretKey)
	            .compact();
	}
}