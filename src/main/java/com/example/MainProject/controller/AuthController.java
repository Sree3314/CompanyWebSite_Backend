
 
package com.example.MainProject.controller;
 
import java.util.Map; // CORRECT: Restore Map import for forgot-password

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MainProject.dto.AuthResponse;
import com.example.MainProject.dto.LoginRequest;
import com.example.MainProject.dto.RegisterRequest;
import com.example.MainProject.dto.ResetPasswordRequest; // CORRECT: Restore ResetPasswordRequest import
import com.example.MainProject.model.User;
import com.example.MainProject.service.AuthService;

import jakarta.validation.Valid;
 
/**
 * REST Controller for authentication operations (register, login, password reset).
 */
@RestController
@RequestMapping("/api/auth")

public class AuthController {
 
    private final AuthService authService;
 
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
 
    /**
     * Handles user registration. Expects a pre-approved employee ID in the DB.
     * Upon successful registration, the account status changes directly to ACTIVE.
     * POST /api/auth/register
     * @param request RegisterRequest DTO.
     * @return ResponseEntity with success message or error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
    	 try {
             User registeredUser = authService.registerUser(request);
             // --- MODIFIED LINE: Return a JSON object for success ---
             // Create a simple Map or a dedicated DTO for success messages if you prefer
             return ResponseEntity.status(HttpStatus.OK)
                                  .body(Map.of("message", "Registration successful. Account is now active.",
                                              "email", registeredUser.getEmail(),
                                              "employeeId", registeredUser.getEmployeeId()));
             // If you had a dedicated DTO for this success message:
             // .body(new RegistrationSuccessResponse("Registration successful. Account is now active.", registeredUser.getEmail()));

         } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
 
    /**
     * Authenticates a user and returns a JWT.
     * POST /api/auth/login
     * @param loginRequest LoginRequest DTO.
     * @return ResponseEntity with AuthResponse (JWT, email, roles) or error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.authenticateUser(loginRequest);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
 
    /**
     * Initiates password reset by sending an OTP to the user's personal email.
     * POST /api/auth/forgot-password
     * @param request A map containing the "personalEmail" field.
     * @return ResponseEntity with success message or error.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String personalEmail = request.get("personalEmail");
        if (personalEmail == null || personalEmail.isBlank()) {
            return ResponseEntity.badRequest().body("Personal email is required.");
        }
        try {
            authService.initiatePasswordReset(personalEmail);
            // Return JSON for success
            return ResponseEntity.ok(Map.of("message", "Password reset OTP sent to your personal email."));
        } catch (RuntimeException e) {
            // Return JSON for error
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
 
    /**
     * Resets the user's password after verifying the OTP.
     * POST /api/auth/reset-password
     * @param request ResetPasswordRequest DTO containing organization email, OTP, and new password.
     * @return ResponseEntity with success message or error.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
    	 try {
             authService.resetPassword(request.getOrganizationEmail(), request.getToken(), request.getNewPassword());
             // Return JSON for success
             return ResponseEntity.ok(Map.of("message", "Password has been reset successfully."));
         } catch (RuntimeException e) {
             // Return JSON for error
             return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
         }
    }
}
 
 