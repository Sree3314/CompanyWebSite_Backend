package com.example.MainProject.service;

import com.example.MainProject.dto.CommentRequest;
import com.example.MainProject.dto.RatingRequest;
import com.example.MainProject.dto.ShowcaseItemDTO;
import com.example.MainProject.model.Upload;
import com.example.MainProject.repository.UploadRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for handling the public exhibition of approved uploads
 * and for manager-specific actions like rating and commenting.
 */
@Service
public class ExhibitionService {

    private final UploadRepository uploadRepository;

    @Autowired
    public ExhibitionService(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    /**
     * Retrieves all uploads that are marked for showcase (isForShowcase = true).
     * This is a public endpoint.
     * @return A list of ShowcaseItemDTOs.
     */
    public List<ShowcaseItemDTO> getAllShowcaseItems() {
        List<Upload> showcaseUploads = uploadRepository.findByIsForShowcaseTrue();
        return showcaseUploads.stream()
                .map(this::convertToShowcaseItemDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the rating for a specific upload. Only accessible by MANAGER role.
     * @param uploadId The ID of the upload to rate.
     * @param ratingRequest DTO containing the new rating.
     * @throws ResponseStatusException if upload not found.
     */
    @Transactional
    public void rateUpload(Long uploadId, RatingRequest ratingRequest) {
        Upload upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found."));

        upload.setRating(ratingRequest.getRating());
        uploadRepository.save(upload);
    }

    /**
     * Adds a comment to a specific upload. Only accessible by MANAGER role.
     * @param uploadId The ID of the upload to comment on.
     * @param commentRequest DTO containing the new comment.
     * @throws ResponseStatusException if upload not found.
     */
    @Transactional
    public void commentUpload(Long uploadId, CommentRequest commentRequest) {
        Upload upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found."));

        upload.setComment(commentRequest.getComment());
        uploadRepository.save(upload);
    }

    /**
     * Helper method to convert an Upload entity to a ShowcaseItemDTO.
     * @param upload The Upload entity.
     * @return The corresponding ShowcaseItemDTO.
     */
    private ShowcaseItemDTO convertToShowcaseItemDTO(Upload upload) {
        String uploaderName = upload.getUser() != null ? upload.getUser().getEmail() : "Unknown";

        return new ShowcaseItemDTO(
            upload.getId().toString(),
            upload.getTitle(),
            upload.getDescription(),
            uploaderName,
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
