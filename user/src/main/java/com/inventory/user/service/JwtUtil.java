package com.inventory.user.service;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import tools.jackson.databind.ObjectMapper;

import java.security.Key;

@Service
public class JwtUtil {
    private final String SECRET_KEY = "basiclittlekeyforourlittlesecretkeyqixisiliveliness";
    private final SecretKey Skey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    
    public String generateToken(String username, String role) {
    	// ensure it follows Spring Security's naming conventions
    	String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
    	
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
        		.claim("role", formattedRole).signWith(Skey)
        		.compact();
    }
    
    public Claims getClaims(String token) {
        try {
        	Claims claims = Jwts.parser()
	        		.verifyWith(Skey)
	       			.build()
	       			.parseSignedClaims(token)
	       			.getPayload();
        	
        	/* debugging
        	ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(claims));
            */
            
            return claims;
        } catch(InvalidKeyException e) {
        	throw new RuntimeException("Invalid JWT signature");
        }
    }
    
    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }
    
    public String extractRole(String token) {
    	return getClaims(token).get("role", String.class);
    }

	public boolean isTokenExpired(String token){
	       return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
	}
	
	
}
