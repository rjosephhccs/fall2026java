package com.inventory.user.dto;

public record LoginRequest(
		String username,
		String password
	) {}
