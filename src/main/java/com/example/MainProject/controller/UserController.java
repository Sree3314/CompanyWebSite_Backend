package com.example.MainProject.controller;

import com.example.MainProject.dto.UserProfileResponse;
import com.example.MainProject.dto.UserProfileUpdateRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user profile management in the MainProject.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Helper method to get the authenticated user's ID.
     * Extracts the UserDetails from the SecurityContext and casts to your custom User model
     * to retrieve the ID.
     * @return The ID of the authenticated user.
     * @throws RuntimeException if user is not authenticated or principal type is unexpected.
     */
    private Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("User not authenticated.");
        }
        // Assuming your User entity is the principal when authenticated via JWT.
        // It implements UserDetails.
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) { // Directly cast to your User model
            return ((User) principal).getId();
        } else if (principal instanceof UserDetails) { // Fallback if Spring Security wraps your User
            String email = ((UserDetails) principal).getUsername();
            User user = userService.getUserRepository().findByEmail(email) // Using UserService's getter for UserRepository
                         .orElseThrow(() -> new RuntimeException("Authenticated user not found in DB by email."));
            return user.getId();
        }
        throw new RuntimeException("Could not retrieve authenticated user ID from principal type: " + principal.getClass().getName());
    }


    /**
     * Retrieves the profile of the currently authenticated user.
     * GET /api/users/me
     * @return ResponseEntity with UserProfileResponse and HttpStatus.OK.
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        try {
            Long userId = getAuthenticatedUserId();
            UserProfileResponse userProfile = userService.getUserProfile(userId);
            return ResponseEntity.ok(userProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates the profile of the currently authenticated user.
     * PUT /api/users/me
     * @param updateRequest UserProfileUpdateRequest DTO with fields to update.
     * @return ResponseEntity with updated UserProfileResponse and HttpStatus.OK.
     */
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateUserProfile(@Valid @RequestBody UserProfileUpdateRequest updateRequest) {
        try {
            Long userId = getAuthenticatedUserId();
            UserProfileResponse updatedProfile = userService.updateUserProfile(userId, updateRequest);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
