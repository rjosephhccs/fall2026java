package com.ims.users.dto;

public record LoginRequest (

    String username,
    String password
) {}