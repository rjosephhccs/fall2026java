package com.inventory.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service

public class JWTService {

    // @Value("${jwt.secret}")
    private String secretKey = "super-secret";

    // @Value("${jwt.expiration}")
    private long expiration = 1000 * 60 * 60;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractClaim(token,Claims::getSubject);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

	public boolean isTokenExpired(String token){
	        return extractClaim(token,Claims::getExpiration).before(new Date());
	}
	
	private <T> T extractClaim(String token, Function<Claims,T> claimsResolved){
	        Claims claim=Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	        return claimsResolved.apply(claim);
	}
	
	/* private Key getSigningKey(){
	        byte[] keys2= Decoders.BASE64.decode(secretKey);
	        return Keys.hmacShaKeyFor(keys2);
	} */
	
	private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}