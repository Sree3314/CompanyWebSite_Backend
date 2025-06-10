package com.example.MainProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

/**
 * JPA Entity for Upload.
 * Represents a piece of work uploaded by a User.
 * Includes project start and end dates, and an external employee ID.
 */
@Entity
@Table(name = "uploads")
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with User (our MainProject's User entity)
    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false) // Foreign key column in 'uploads' table, linking to users
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(name = "project_duration", length = 100)
    private String projectDuration;

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;

    @Column(name = "file_url", length = 2000)
    private String fileUrl;

    @Column(name = "is_for_showcase")
    private Boolean isForShowcase = false; // Default to not for showcase in this phase

    @Column(name = "approval_status", length = 50)
    private String approvalStatus = "pending"; // Default to pending

    @Column
    private Integer rating;

    @Column(length = 1000)
    private String comment;

    @Column(name = "started_date", nullable = false)
    private LocalDate startedDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "external_employee_id", length = 50, nullable = false)
    private String externalEmployeeId;

    // No-argument constructor
    public Upload() {
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getProjectDuration() {
        return projectDuration;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Boolean getIsForShowcase() {
        return isForShowcase;
    }

    public String getApprovalStatus() {
        return approvalStatus;
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

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setIsForShowcase(Boolean forShowcase) {
        isForShowcase = forShowcase;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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
        Upload upload = (Upload) o;
        return Objects.equals(id, upload.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Upload{" +
               "id=" + id +
               ", user=" + (user != null ? user.getEmail() : "null") +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", projectDuration='" + projectDuration + '\'' +
               ", uploadDate=" + uploadDate +
               ", fileUrl='" + fileUrl + '\'' +
               ", isForShowcase=" + isForShowcase +
               ", approvalStatus='" + approvalStatus + '\'' +
               ", rating=" + rating +
               ", comment='" + comment + '\'' +
               ", startedDate=" + startedDate +
               ", endDate=" + endDate +
               ", externalEmployeeId='" + externalEmployeeId + '\'' +
               '}';
    }
}
