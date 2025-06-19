package com.example.MainProject.dto;

import java.util.List;
import java.util.Set;

public class AuthResponse {
	private String jwtToken;
	private String email;
	private List<String> roles;
	private Long employeeId;

	public AuthResponse(String jwtToken, String email, List<String> roles,long employeeId) {
		this.jwtToken = jwtToken;
		this.email = email;
		this.roles = roles;
		this.employeeId=employeeId;
	}

	// Getters and Setters
	
	public String getJwtToken() {
		return jwtToken;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
