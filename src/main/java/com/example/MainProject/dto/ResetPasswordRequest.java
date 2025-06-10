package com.example.MainProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
	@NotBlank(message = "Organization Email is required")
	@Email(message = "Invalid email format")
	private String organizationEmail; // The user's company email
	@NotBlank(message = "OTP/Token is required")
	private String token; // The OTP/token received
	@NotBlank(message = "New password is required")
	@Size(min = 6, message = "New password must be at least 6 characters long")
	private String newPassword;

	// Getters and Setters
	public String getOrganizationEmail() {
		return organizationEmail;
	}

	public void setOrganizationEmail(String organizationEmail) {
		this.organizationEmail = organizationEmail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
