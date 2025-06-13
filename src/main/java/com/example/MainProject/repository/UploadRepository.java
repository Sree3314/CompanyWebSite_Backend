package com.example.MainProject.repository;

import com.example.MainProject.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {
	List<Upload> findByUserEmployeeId(Long employeeId);
 // Find uploads by the internal user ID

    List<Upload> findByIsForShowcaseTrue(); // For the future exhibition module

    Optional<Upload> findByIdAndUserEmployeeId(Long uploadId, Long employeeId); // Find specific upload by ID and its uploader
}
