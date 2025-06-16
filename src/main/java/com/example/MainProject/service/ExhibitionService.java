package com.example.MainProject.service;
 
import com.example.MainProject.dto.CommentRequest;

import com.example.MainProject.dto.RatingRequest;

import com.example.MainProject.dto.ShowcaseItemDTO;

import com.example.MainProject.model.Upload;

import com.example.MainProject.repository.UploadRepository;

import jakarta.persistence.EntityManager; // Needed for dynamic query

import jakarta.persistence.PersistenceContext; // Needed for dynamic query

import jakarta.persistence.criteria.Predicate; // Needed for dynamic query

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
 
import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;
 
/**

* Service layer for handling the public exhibition of approved uploads

* and for manager-specific actions like rating and commenting.

*/

@Service

public class ExhibitionService {
 
    private final UploadRepository uploadRepository;
 
    @PersistenceContext // Inject EntityManager for dynamic queries

    private EntityManager entityManager;
 
    @Autowired

    public ExhibitionService(UploadRepository uploadRepository) {

        this.uploadRepository = uploadRepository;

    }
 
    /**

     * Retrieves all uploads that are marked for showcase (isForShowcase = true and approvalStatus = 'approved').

     * This is a public endpoint.

     * @return A list of ShowcaseItemDTOs.

     */

    public List<ShowcaseItemDTO> getAllShowcaseItems() {

        // Corrected to fetch only approved showcase items by default

        List<Upload> showcaseUploads = uploadRepository.findByIsForShowcaseTrueAndApprovalStatus("approved");

        return showcaseUploads.stream()

                .map(this::convertToShowcaseItemDTO)

                .collect(Collectors.toList());

    }
 
    /**

     * Retrieves a single upload by its ID for public view, if it's marked for showcase and approved.

     * @param uploadId The ID of the upload.

     * @return ShowcaseItemDTO.

     * @throws ResponseStatusException if upload not found or not approved for showcase.

     */

    public ShowcaseItemDTO getShowcaseItemById(Long uploadId) {

        Upload upload = uploadRepository.findById(uploadId)

                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found."));
 
        if (!upload.getIsForShowcase() || !"approved".equals(upload.getApprovalStatus())) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Upload not found or not available for public view.");

        }

        return convertToShowcaseItemDTO(upload);

    }
 
 
    /**

     * NEW: Retrieves uploads marked for showcase, with optional filtering by externalEmployeeId and/or firstName.

     * This is a public endpoint.

     * @param externalEmployeeId Optional: The external employee ID to filter by.

     * @param firstName Optional: The first name of the uploader to filter by.

     * @return A list of ShowcaseItemDTOs.

     */

    public List<ShowcaseItemDTO> getFilteredShowcaseItems(Long externalEmployeeId, String firstName) {

        var cb = entityManager.getCriteriaBuilder();

        var cq = cb.createQuery(Upload.class);

        var root = cq.from(Upload.class);
 
        // Join with User entity to filter by firstName

        root.fetch("user"); // Eagerly fetch user to avoid N+1 problem
 
        List<Predicate> predicates = new ArrayList<>();
 
        // Always filter by isForShowcase = true and approvalStatus = 'approved'

        predicates.add(cb.isTrue(root.get("isForShowcase")));

        predicates.add(cb.equal(root.get("approvalStatus"), "approved"));
 
        // Add filter for externalEmployeeId if provided

        if (externalEmployeeId != null) {

            predicates.add(cb.equal(root.get("user").get("employeeId"), externalEmployeeId));

        }
 
        // Add filter for firstName if provided (case-insensitive)

        if (firstName != null && !firstName.trim().isEmpty()) {

            predicates.add(cb.like(cb.lower(root.get("user").get("firstName")), "%" + firstName.toLowerCase() + "%"));

        }
 
        cq.where(predicates.toArray(new Predicate[0]));
 
        List<Upload> filteredUploads = entityManager.createQuery(cq).getResultList();
 
        return filteredUploads.stream()

                .map(this::convertToShowcaseItemDTO)

                .collect(Collectors.toList());

    }
 
 
    /**

     * Allows a MANAGER to rate a specific upload.

     * @param uploadId The ID of the upload to rate.

     * @param ratingRequest DTO containing the rating (1-5).

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

     * Allows a MANAGER to add a comment to a specific upload.

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

        // Populate uploader's first and last name from the linked User entity

        String uploaderFirstName = upload.getUser() != null ? upload.getUser().getFirstName() : null;

        String uploaderLastName = upload.getUser() != null ? upload.getUser().getLastName() : null;

        Long externalEmployeeId = upload.getUser() != null ? upload.getUser().getEmployeeId() : null;
 
        return new ShowcaseItemDTO(

            upload.getId().toString(), // Upload's own ID

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

            externalEmployeeId // External Employee ID from linked User

        );

    }

}

 