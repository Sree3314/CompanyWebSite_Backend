package com.example.MainProject.dto;
 
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Min; // Added for Long

import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
 
import java.time.LocalDate;

import java.util.Objects;
 
/**

* DTO for receiving upload data from a user.

*/

public class UploadRequest {

    @NotBlank(message = "Title is required")

    @Size(max = 255, message = "Title cannot exceed 255 characters")

    private String title;
 
    @NotBlank(message = "Description is required")

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")

    private String description;
 
    @NotBlank(message = "Project duration is required")

    @Size(max = 100, message = "Project duration cannot exceed 100 characters")

    private String projectDuration;
 
    @NotBlank(message = "File URL is required")

    @Size(max = 2000, message = "File URL cannot exceed 2000 characters")

    private String fileUrl;
 
    @NotNull(message = "Started date is required")

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate startedDate;
 
    @NotNull(message = "End date is required")

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)

    private LocalDate endDate;
 
    @NotNull(message = "External Employee ID is required")

    @Min(value = 1, message = "External Employee ID must be a positive number")

    private Long externalEmployeeId; // Changed from String to Long
 
    // No-argument constructor

    public UploadRequest() {

    }
 
    // All-argument constructor

    public UploadRequest(String title, String description, String projectDuration, String fileUrl,

                         LocalDate startedDate, LocalDate endDate, Long externalEmployeeId) {

        this.title = title;

        this.description = description;

        this.projectDuration = projectDuration;

        this.fileUrl = fileUrl;

        this.startedDate = startedDate;

        this.endDate = endDate;

        this.externalEmployeeId = externalEmployeeId;

    }
 
    // Getters

    public String getTitle() {

        return title;

    }
 
    public String getDescription() {

        return description;

    }
 
    public String getProjectDuration() {

        return projectDuration;

    }
 
    public String getFileUrl() {

        return fileUrl;

    }
 
    public LocalDate getStartedDate() {

        return startedDate;

    }
 
    public LocalDate getEndDate() {

        return endDate;

    }
 
    public Long getExternalEmployeeId() {

        return externalEmployeeId;

    }
 
    // Setters

    public void setTitle(String title) {

        this.title = title;

    }
 
    public void setDescription(String description) {

        this.description = description;

    }
 
    public void setProjectDuration(String projectDuration) {

        this.projectDuration = projectDuration;

    }
 
    public void setFileUrl(String fileUrl) {

        this.fileUrl = fileUrl;

    }
 
    public void setStartedDate(LocalDate startedDate) {

        this.startedDate = startedDate;

    }
 
    public void setEndDate(LocalDate endDate) {

        this.endDate = endDate;

    }
 
    public void setExternalEmployeeId(Long externalEmployeeId) {

        this.externalEmployeeId = externalEmployeeId;

    }
 
    // --- equals(), hashCode(), toString() ---

    @Override

    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UploadRequest that = (UploadRequest) o;

        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(projectDuration, that.projectDuration) && Objects.equals(fileUrl, that.fileUrl) && Objects.equals(startedDate, that.startedDate) && Objects.equals(endDate, that.endDate) && Objects.equals(externalEmployeeId, that.externalEmployeeId);

    }
 
    @Override

    public int hashCode() {

        return Objects.hash(title, description, projectDuration, fileUrl, startedDate, endDate, externalEmployeeId);

    }
 
    @Override

    public String toString() {

        return "UploadRequest{" +

               "title='" + title + '\'' +

               ", description='" + description + '\'' +

               ", projectDuration='" + projectDuration + '\'' +

               ", fileUrl='" + fileUrl + '\'' +

               ", startedDate=" + startedDate +

               ", endDate=" + endDate +

               ", externalEmployeeId=" + externalEmployeeId +

               '}';

    }

}

 