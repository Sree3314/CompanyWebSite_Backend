package com.example.MainProject.controller;
 
import com.example.MainProject.dto.UploadDTO;
import com.example.MainProject.dto.UploadRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.service.UploadService;
 
import jakarta.validation.Valid; // Still needed for validation triggering
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
 
// Imports for validation error handling
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
* REST Controller for authenticated user uploads.
* Handles creating new uploads and retrieving specific uploads by ID.
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
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated.");
        }
 
        // The principal is our custom User object since it implements UserDetails
        if (authentication.getPrincipal() instanceof User userPrincipal) {
            return userPrincipal;
        }
 
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve authenticated user details.");
    }
 
 
    /**
     * Creates a new upload by the authenticated user.
     * POST /api/uploads
     * @param uploadRequest DTO containing upload details.
     * @return ResponseEntity with the created UploadDTO and HttpStatus.CREATED,
     * or ResponseEntity with error map and HttpStatus.BAD_REQUEST for validation failures.
     */
    @PostMapping
    public ResponseEntity<?> createUpload( // Changed return type to '?' for flexibility
            @Valid @RequestBody UploadRequest uploadRequest) { // @Valid annotation remains
        try {
            // The externalEmployeeId is expected to be in the UploadRequest DTO
            UploadDTO createdUpload = uploadService.createUpload(uploadRequest);
            return new ResponseEntity<>(createdUpload, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            throw e; // Re-throw handled exceptions (e.g., UNAUTHORIZED from getAuthenticatedUser)
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Backend: An unexpected error occurred while creating upload: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed due to an internal error: " + e.getMessage());
        }
    }
 
    /**
     * Retrieves a list of all uploads for the currently authenticated user.
     * GET /api/uploads/my-uploads
     * Requires authentication.
     * @return ResponseEntity with a list of UploadDTOs and HttpStatus.OK.
     */
    @GetMapping("/my-uploads")
    public ResponseEntity<List<UploadDTO>> getMyUploads() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<UploadDTO> myUploads = uploadService.getUploadsByEmployeeId(authenticatedUser.getEmployeeId());
            return ResponseEntity.ok(myUploads);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
 
    /**
     * Retrieves a specific upload by ID for the authenticated user (owner).
     * GET /api/uploads/{uploadId}/mine
     * This endpoint is distinct from the public /api/uploads/{uploadId} in ExhibitionController.
     * @param uploadId The ID of the upload to retrieve.
     * @return ResponseEntity with UploadDTO and HttpStatus.OK.
     */
    @GetMapping("/{uploadId}/mine") // Added a specific path for 'my' uploads by ID
    public ResponseEntity<UploadDTO> getMyUploadById(@PathVariable Long uploadId) {
        try {
            User authenticatedUser = getAuthenticatedUser();
            UploadDTO upload = uploadService.getUploadByIdAndUserEmployeeId(uploadId, authenticatedUser.getEmployeeId());
            return ResponseEntity.ok(upload);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
 
 
    @DeleteMapping("/{uploadId}") // Standard RESTful DELETE mapping
    public ResponseEntity<Void> deleteUpload(@PathVariable Long uploadId) {
        try {
            User authenticatedUser = getAuthenticatedUser(); // Get the full user object for auth check
            uploadService.deleteUpload(uploadId, authenticatedUser);
            return ResponseEntity.noContent().build(); // 204 No Content for successful deletion
        } catch (ResponseStatusException e) {
            throw e; // Re-throw handled exceptions (e.g., NOT_FOUND, FORBIDDEN)
        } catch (Exception e) {
            // Log unexpected errors
            System.err.println("Backend: An unexpected error occurred while deleting upload " + uploadId + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
 
 