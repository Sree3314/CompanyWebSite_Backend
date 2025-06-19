package com.example.MainProject.service;

import com.example.MainProject.dto.UploadDTO;
import com.example.MainProject.dto.UploadRequest;
import com.example.MainProject.model.Upload;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.UploadRepository;
import com.example.MainProject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for handling user-initiated upload creation and retrieval.
 * Uploads are handled by an authenticated user, linking to the User entity.
 */
@Service
public class UploadService {

    private final UploadRepository uploadRepository;
    private final UserRepository userRepository;

    @Autowired
    public UploadService(UploadRepository uploadRepository, UserRepository userRepository) {
        this.uploadRepository = uploadRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new upload, linked to the User identified by the externalEmployeeId in the request.
     *
     * @param uploadRequest The DTO containing upload details and the externalEmployeeId of the uploader.
     * @return UploadDTO of the created upload.
     */
    @Transactional
    public UploadDTO createUpload(UploadRequest uploadRequest) {
        // Find the user by their employeeId (which is the primary key for User)
        User user = userRepository.findByEmployeeId(uploadRequest.getExternalEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with Employee ID: " + uploadRequest.getExternalEmployeeId()));

        Upload newUpload = new Upload();
        newUpload.setUser(user); // Link the upload to the found User entity
        newUpload.setTitle(uploadRequest.getTitle());
        newUpload.setDescription(uploadRequest.getDescription());
        newUpload.setProjectDuration(uploadRequest.getProjectDuration());
        newUpload.setFileUrl(uploadRequest.getFileUrl());
        newUpload.setStartedDate(uploadRequest.getStartedDate());
        newUpload.setEndDate(uploadRequest.getEndDate());
        newUpload.setUploadDate(LocalDate.now()); // Set upload date to now

        // Set default values for showcase and approval as per requirement
        newUpload.setIsForShowcase(true);
        newUpload.setApprovalStatus("approved");

        Upload savedUpload = uploadRepository.save(newUpload);
        return convertToUploadDTO(savedUpload);
    }

    /**
     * Retrieves all uploads by a specific user's employee ID.
     *
     * @param employeeId The employee ID of the user.
     * @return A list of UploadDTOs.
     * @throws ResponseStatusException if the user is not found.
     */
    public List<UploadDTO> getUploadsByEmployeeId(Long employeeId) { // Renamed param for clarity
        // Find the user by their employeeId
        User user = userRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with Employee ID: " + employeeId));

        List<Upload> uploads = uploadRepository.findByUser(user); // Now assumes this method exists in UploadRepository
        return uploads.stream()
                .map(this::convertToUploadDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single upload by its ID, ensuring it belongs to the specified user's employee ID.
     *
     * @param uploadId The ID of the upload.
     * @param requestingUserEmployeeId The employee ID of the authenticated user requesting the upload.
     * @return UploadDTO of the found upload.
     * @throws ResponseStatusException if upload not found or user is not the owner.
     */
    public UploadDTO getUploadByIdAndUserEmployeeId(Long uploadId, Long requestingUserEmployeeId) { // Renamed param for clarity
        Upload upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found with ID: " + uploadId));

        if (!upload.getUser().getEmployeeId().equals(requestingUserEmployeeId)) { // Check owner by employeeId
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this upload.");
        }
        return convertToUploadDTO(upload);
    }

    /**
     * Deletes an upload.
     *
     * @param uploadId The ID of the upload to delete.
     * @param authenticatedUser The authenticated User object (used for authorization).
     * @throws ResponseStatusException if upload not found, or user not authorized.
     */
    @Transactional // Ensure delete operation is transactional
    public void deleteUpload(Long uploadId, User authenticatedUser) {
        // 1. Find the upload
        Upload upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found with ID: " + uploadId));

        // 2. Perform authorization check
        // Check if the authenticated user is the owner OR if they are a MANAGER
        if (!upload.getUser().getEmployeeId().equals(authenticatedUser.getEmployeeId()) ) {    // And not a manager
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this upload.");
        }

        // 3. Delete the upload
        uploadRepository.delete(upload);
    }

    /**
     * Helper method to convert an Upload entity to an UploadDTO.
     *
     * @param upload The Upload entity.
     * @return The corresponding UploadDTO.
     */
    private UploadDTO convertToUploadDTO(Upload upload) {
        // Get user's first and last name for the DTO
        String uploaderFirstName = upload.getUser() != null ? upload.getUser().getFirstName() : null;
        String uploaderLastName = upload.getUser() != null ? upload.getUser().getLastName() : null;

        return new UploadDTO(
            upload.getId().toString(), // Upload ID as String
            upload.getTitle(),
            upload.getDescription(),
            uploaderFirstName,
            uploaderLastName,
            upload.getUploadDate(),
            upload.getFileUrl(),
            upload.getProjectDuration(),
            upload.getRating(),
            upload.getComment(),
            upload.getStartedDate(),
            upload.getEndDate(),
            upload.getUser().getEmployeeId() // External Employee ID from linked User
        );
    }
}