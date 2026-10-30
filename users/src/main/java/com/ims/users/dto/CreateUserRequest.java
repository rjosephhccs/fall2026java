package com.ims.users.dto;

public record CreateUserRequest (

    String username,
    String password,
    String role,

    String firstName,
    String lastName,
    String email
) {}