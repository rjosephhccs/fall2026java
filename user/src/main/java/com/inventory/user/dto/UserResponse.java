package com.inventory.user.dto;

import java.time.LocalDateTime;

public record UserResponse(
		Integer userId,
        String username,
        String role,
        String firstName,
        String lastName,
        String email,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        ) {}
