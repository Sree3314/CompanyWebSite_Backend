package com.example.MainProject.controller;

import com.example.MainProject.dto.UploadDTO;
import com.example.MainProject.dto.UploadRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST Controller for authenticated user uploads.
 * Handles creating new uploads and retrieving specific uploads by ID.
 * This controller now directly interacts with the authenticated user context.
 */
@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * Helper method to get the authenticated User object directly from the SecurityContext.
     * This ensures the upload is linked to the user who is currently logged in.
     * @return The authenticated User object.
     * @throws ResponseStatusException if user is not authenticated or principal type is unexpected.
     */
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated.");
        }
        // Assuming your CustomUserDetailsService returns your User entity as the principal
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        // Fallback: If for some reason the principal is UserDetails (e.g., from CustomUserDetailsService directly creating it)
        // then fetch the full User entity from DB.
        // For now, assuming principal is User as per our setup for CustomUserDetailsService.
        // If this throws an error, we might need to adjust CustomUserDetailsService or fetch the user from DB.
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authenticated principal is not of type User. Cannot retrieve user details.");
    }


    /**
     * Creates a new upload for the authenticated user.
     * POST /api/uploads
     * Requires authentication.
     * @param uploadRequest DTO containing upload details.
     * @return ResponseEntity with the created UploadDTO and HttpStatus.CREATED.
     */
    @PostMapping
    public ResponseEntity<UploadDTO> createUpload(@Valid @RequestBody UploadRequest uploadRequest) {
        try {
            User authenticatedUser = getAuthenticatedUser(); // Get the authenticated user
            UploadDTO createdUpload = uploadService.createUpload(uploadRequest, authenticatedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUpload);
        } catch (ResponseStatusException e) {
            throw e; // Re-throw handled exceptions
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a specific upload by its ID.
     * Only allows retrieval if the upload belongs to the authenticated user.
     * GET /api/uploads/{uploadId}
     * Requires authentication.
     * @param uploadId The ID of the upload to retrieve.
     * @return ResponseEntity with the UploadDTO and HttpStatus.OK.
     */
    @GetMapping("/{uploadId}")
    public ResponseEntity<UploadDTO> getUploadById(@PathVariable Long uploadId) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            UploadDTO upload = uploadService.getUploadByIdAndUserId(uploadId, authenticatedUser.getId());
            return ResponseEntity.ok(upload);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all uploads for the authenticated user.
     * GET /api/uploads/my-uploads
     * Requires authentication.
     * @return ResponseEntity with a list of UploadDTOs and HttpStatus.OK.
     */
    @GetMapping("/my-uploads")
    public ResponseEntity<List<UploadDTO>> getMyUploads() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<UploadDTO> myUploads = uploadService.getUploadsByUserId(authenticatedUser.getId());
            return ResponseEntity.ok(myUploads);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
