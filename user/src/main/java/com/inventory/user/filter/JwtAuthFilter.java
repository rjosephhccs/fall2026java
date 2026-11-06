package com.inventory.user.filter;

import com.inventory.user.service.UserService;
import com.inventory.user.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService customUserDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws ServletException, IOException{
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String username;

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterchain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        username=jwtService.extractUsername(jwt);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterchain.doFilter(request,response);
    }
}