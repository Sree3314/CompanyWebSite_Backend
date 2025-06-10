package com.example.MainProject.dto;

import java.util.Set;

public class AuthResponse {
	private String jwtToken;
	private String email;
	private Set<String> roles;

	public AuthResponse(String jwtToken, String email, Set<String> roles) {
		this.jwtToken = jwtToken;
		this.email = email;
		this.roles = roles;
	}

	// Getters and Setters
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
