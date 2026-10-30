package com.ims.users.dto;

public record LoginResponse (

    String token,
    String username,
    String role
) {}