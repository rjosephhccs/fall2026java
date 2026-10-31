package com.inventory.user.dto;

public record CreateUserRequest(
        String username,
        String passwordHash,
        String role,
        String firstName,
        String lastName,
        String email,
        Boolean isActive
) {
}