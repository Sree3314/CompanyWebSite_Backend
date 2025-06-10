package com.example.MainProject.service;

import com.example.MainProject.dto.UploadDTO;
import com.example.MainProject.dto.UploadRequest;
import com.example.MainProject.model.Upload;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.UploadRepository;
import com.example.MainProject.repository.UserRepository; // Still needed for injection
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
public class UploadService { // Renamed from UserUploadService (if it existed)

    private final UploadRepository uploadRepository;
    private final UserRepository userRepository; // Keep this, even if not directly finding by externalId now

    @Autowired
    public UploadService(UploadRepository uploadRepository, UserRepository userRepository) {
        this.uploadRepository = uploadRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new upload, linked to the provided authenticated User.
     * The externalEmployeeId from the request is stored but the primary linking is via the authenticated User object.
     * @param uploadRequest DTO containing upload details.
     * @param uploader The authenticated User object (from SecurityContext).
     * @return The created UploadDTO.
     */
    @Transactional
    public UploadDTO createUpload(UploadRequest uploadRequest, User uploader) {
        Upload newUpload = new Upload();
        newUpload.setTitle(uploadRequest.getTitle());
        newUpload.setDescription(uploadRequest.getDescription());
        newUpload.setProjectDuration(uploadRequest.getProjectDuration());
        newUpload.setFileUrl(uploadRequest.getFileUrl());
        newUpload.setUser(uploader); // Link directly to the authenticated User entity
        newUpload.setUploadDate(LocalDate.now());
        newUpload.setIsForShowcase(false); // Default to false in this phase, approval pending
        newUpload.setApprovalStatus("pending"); // Default to pending
        newUpload.setStartedDate(uploadRequest.getStartedDate());
        newUpload.setEndDate(uploadRequest.getEndDate());
        newUpload.setExternalEmployeeId(uploadRequest.getExternalEmployeeId()); // Store for data consistency

        Upload savedUpload = uploadRepository.save(newUpload);

        return convertToUploadDTO(savedUpload);
    }

    /**
     * Retrieves an upload by its ID, ensuring it belongs to the specified user ID.
     * This is for a user viewing their own uploads or a manager viewing specific uploads.
     * @param uploadId The ID of the upload.
     * @param userId The ID of the user requesting the upload (for ownership check).
     * @return The UploadDTO.
     * @throws ResponseStatusException if upload not found or does not belong to the user.
     */
    public UploadDTO getUploadByIdAndUserId(Long uploadId, Long userId) {
        Upload upload = uploadRepository.findByIdAndUserId(uploadId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found or you do not have permission to view this upload."));
        return convertToUploadDTO(upload);
    }

    /**
     * Retrieves all uploads for a specific user ID.
     * @param userId The ID of the user whose uploads are to be retrieved.
     * @return A list of UploadDTOs.
     */
    public List<UploadDTO> getUploadsByUserId(Long userId) {
        List<Upload> uploads = uploadRepository.findByUserId(userId);
        return uploads.stream()
                .map(this::convertToUploadDTO)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to convert an Upload entity to an UploadDTO.
     * Ensures the response format is consistent for display.
     * @param upload The Upload entity.
     * @return The corresponding UploadDTO.
     */
    private UploadDTO convertToUploadDTO(Upload upload) {
        String userName = upload.getUser() != null ? upload.getUser().getEmail() : "Unknown"; // Use getEmail for userName
        Long internalUserId = upload.getUser() != null ? upload.getUser().getId() : null;

        return new UploadDTO(
            upload.getId().toString(),
            upload.getTitle(),
            upload.getDescription(),
            internalUserId,
            userName,
            upload.getUploadDate(),
            upload.getFileUrl(),
            upload.getProjectDuration(),
            upload.getRating(),
            upload.getComment(),
            upload.getStartedDate(),
            upload.getEndDate(),
            upload.getExternalEmployeeId()
        );
    }
}
