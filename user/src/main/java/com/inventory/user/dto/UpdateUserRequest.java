package com.inventory.user.dto;

public record UpdateUserRequest(
		String role,
        String firstName,
        String lastName,
        String email,
        Boolean isActive
        ) {}
