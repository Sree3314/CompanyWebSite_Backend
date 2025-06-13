
package com.example.MainProject.dto;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;
 
/**
 * DTO for the password reset request, used to verify OTP and set new password.
 */
public class ResetPasswordRequest {
    @NotBlank(message = "Organization email is required")
    @Email(message = "Organization email must be a valid email address")
    private String organizationEmail;
 
    @NotBlank(message = "OTP is required")
    @Size(min = 6, max = 6, message = "OTP must be 6 digits")
    private String token; // This field holds the OTP
 
    @NotBlank(message = "New password is required")
    @Size(min = 6, max = 255, message = "New password must be between 6 and 255 characters")
    private String newPassword;
 
    // No-argument constructor
    public ResetPasswordRequest() {
    }
 
    // All-argument constructor
    public ResetPasswordRequest(String organizationEmail, String token, String newPassword) {
        this.organizationEmail = organizationEmail;
        this.token = token;
        this.newPassword = newPassword;
    }
 
    // --- Getters ---
    public String getOrganizationEmail() {
        return organizationEmail;
    }
 
    public String getToken() {
        return token;
    }
 
    public String getNewPassword() {
        return newPassword;
    }
 
    // --- Setters ---
    public void setOrganizationEmail(String organizationEmail) {
        this.organizationEmail = organizationEmail;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetPasswordRequest that = (ResetPasswordRequest) o;
        return Objects.equals(organizationEmail, that.organizationEmail) &&
               Objects.equals(token, that.token) &&
               Objects.equals(newPassword, that.newPassword);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(organizationEmail, token, newPassword);
    }
 
    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
               "organizationEmail='" + organizationEmail + '\'' +
               ", token='" + token + '\'' +
               ", newPassword='" + newPassword + '\'' +
               '}';
    }
}
 
 