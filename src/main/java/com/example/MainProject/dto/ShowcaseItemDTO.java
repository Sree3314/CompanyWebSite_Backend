package com.example.MainProject.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO for representing a project on the public exhibition/showcase.
 * Contains only public-facing information and includes rating/comment.
 */
public class ShowcaseItemDTO {
    private String uploadId;
    private String title;
    private String description;
    private String uploaderName; // Email of the user who uploaded it
    private LocalDate uploadDate;
    private String fileUrl;
    private String projectDuration;
    private Integer rating;
    private String comment;
    private LocalDate startedDate;
    private LocalDate endDate;
    private String externalEmployeeId;

    // No-argument constructor
    public ShowcaseItemDTO() {
    }

    // All-argument constructor
    public ShowcaseItemDTO(String uploadId, String title, String description, String uploaderName,
                           LocalDate uploadDate, String fileUrl, String projectDuration, Integer rating,
                           String comment, LocalDate startedDate, LocalDate endDate, String externalEmployeeId) {
        this.uploadId = uploadId;
        this.title = title;
        this.description = description;
        this.uploaderName = uploaderName;
        this.uploadDate = uploadDate;
        this.fileUrl = fileUrl;
        this.projectDuration = projectDuration;
        this.rating = rating;
        this.comment = comment;
        this.startedDate = startedDate;
        this.endDate = endDate;
        this.externalEmployeeId = externalEmployeeId;
    }

    // --- Getters ---
    public String getUploadId() {
        return uploadId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getProjectDuration() {
        return projectDuration;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getStartedDate() {
        return startedDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getExternalEmployeeId() {
        return externalEmployeeId;
    }

    // --- Setters (optional, typically DTOs are immutable but useful for mappers) ---
    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStartedDate(LocalDate startedDate) {
        this.startedDate = startedDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setExternalEmployeeId(String externalEmployeeId) {
        this.externalEmployeeId = externalEmployeeId;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowcaseItemDTO that = (ShowcaseItemDTO) o;
        return Objects.equals(uploadId, that.uploadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploadId);
    }

    @Override
    public String toString() {
        return "ShowcaseItemDTO{" +
               "uploadId='" + uploadId + '\'' +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", uploaderName='" + uploaderName + '\'' +
               ", uploadDate=" + uploadDate +
               ", fileUrl='" + fileUrl + '\'' +
               ", projectDuration='" + projectDuration + '\'' +
               ", rating=" + rating +
               ", comment='" + comment + '\'' +
               ", startedDate=" + startedDate +
               ", endDate=" + endDate +
               ", externalEmployeeId='" + externalEmployeeId + '\'' +
               '}';
    }
}
