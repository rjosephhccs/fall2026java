package com.inventory.user.service;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {
    private static final String SECRET_KEY = "basiclittlekeyforourlittlesecretkeyqixisiliveliness";
    private static final SecretKey Skey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));

    
    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    public static boolean validateToken(String token) {
    	
        try {
        	Jwts.parser().verifyWith(Skey).build().parseUnsecuredClaims(token);
            return true;
        } catch (SecurityException e) {
            System.err.println("Invalid JWT signature");
        } catch (Exception e) {
            System.err.println("Invalid JWT claims");
        }
        return false;
    }
}
