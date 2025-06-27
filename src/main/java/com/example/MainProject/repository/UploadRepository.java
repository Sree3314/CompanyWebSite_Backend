package com.example.MainProject.repository;
 
import com.example.MainProject.model.Upload;

import com.example.MainProject.model.User; // Import User

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
 
import java.util.List;

import java.util.Optional;
 
@Repository

public interface UploadRepository extends JpaRepository<Upload, Long> {

    // Find uploads by the linked User entity

    List<Upload> findByUser(User user); // NEW: Added this method
 
    // Find uploads by the internal user ID (autoId) - this implicitly uses User.autoId

    // List<Upload> findByUserAutoId(Long autoId); // This might also work but findByUser is more direct
 
    List<Upload> findByIsForShowcaseTrue(); // For the future exhibition module
 
    // Find uploads that are for showcase and approved

    List<Upload> findByIsForShowcaseTrueAndApprovalStatus(String approvalStatus); // Added for ExhibitionService
 
    // Find specific upload by ID and its uploader's autoId

    Optional<Upload> findByIdAndUserEmployeeId(Long uploadId, Long userAutoId); // Updated to use User.autoId

    



 // NEW: Find uploads that are for showcase, approved, and have a non-null rating
 List<Upload> findByIsForShowcaseTrueAndApprovalStatusAndRatingIsNotNull(String approvalStatus);


}

 
