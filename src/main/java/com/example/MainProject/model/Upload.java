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

* Includes project start and end dates.

*/

@Entity

@Table(name = "uploads")

public class Upload {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    // Many-to-one relationship with User (our MainProject's User entity)

    // Now references the new 'autoId' primary key of the User table

    @ManyToOne

    @JoinColumn(name = "user_auto_id", referencedColumnName = "employeeId", nullable = false) // Updated FK reference

    private User user;
 
    @Column(nullable = false)

    private String title;
 
    @Column(nullable = false, length = 2000)

    private String description;
 
    @Column(name = "project_duration", length = 100)

    private String projectDuration;
 
    @Column(name = "upload_date", nullable = false)

    private LocalDate uploadDate;
 
    @Column(name = "file_url", length = 2000, nullable = false)

    private String fileUrl;
 
    @Column(name = "is_for_showcase", nullable = false)

    private Boolean isForShowcase; // For public exhibition
 
    @Column(name = "approval_status", length = 50, nullable = false)

    private String approvalStatus; // E.g., "pending", "approved", "rejected"
 
    @Column(name = "rating")

    private Integer rating; // Rating from 1-5, by managers
 
    @Column(name = "comment", length = 1000)

    private String comment; // Comment from managers
 
    @Column(name = "started_date")

    private LocalDate startedDate;
 
    @Column(name = "end_date")

    private LocalDate endDate;
 
    // No longer needs 'externalEmployeeId' directly, as it's accessible via 'user.getExternalEmployeeId()'
 
    // Constructors

    public Upload() {

        this.uploadDate = LocalDate.now();

        this.isForShowcase = false; // Default

        this.approvalStatus = "pending"; // Default

    }
 
    // Getters and Setters

    public Long getId() {

        return id;

    }
 
    public void setId(Long id) {

        this.id = id;

    }
 
    public User getUser() {

        return user;

    }
 
    public void setUser(User user) {

        this.user = user;

    }
 
    public String getTitle() {

        return title;

    }
 
    public void setTitle(String title) {

        this.title = title;

    }
 
    public String getDescription() {

        return description;

    }
 
    public void setDescription(String description) {

        this.description = description;

    }
 
    public String getProjectDuration() {

        return projectDuration;

    }
 
    public void setProjectDuration(String projectDuration) {

        this.projectDuration = projectDuration;

    }
 
    public LocalDate getUploadDate() {

        return uploadDate;

    }
 
    public void setUploadDate(LocalDate uploadDate) {

        this.uploadDate = uploadDate;

    }
 
    public String getFileUrl() {

        return fileUrl;

    }
 
    public void setFileUrl(String fileUrl) {

        this.fileUrl = fileUrl;

    }
 
    public Boolean getIsForShowcase() {

        return isForShowcase;

    }
 
    public void setIsForShowcase(Boolean forShowcase) {

        isForShowcase = forShowcase;

    }
 
    public String getApprovalStatus() {

        return approvalStatus;

    }
 
    public void setApprovalStatus(String approvalStatus) {

        this.approvalStatus = approvalStatus;

    }
 
    public Integer getRating() {

        return rating;

    }
 
    public void setRating(Integer rating) {

        this.rating = rating;

    }
 
    public String getComment() {

        return comment;

    }
 
    public void setComment(String comment) {

        this.comment = comment;

    }
 
    public LocalDate getStartedDate() {

        return startedDate;

    }
 
    public void setStartedDate(LocalDate startedDate) {

        this.startedDate = startedDate;

    }
 
    public LocalDate getEndDate() {

        return endDate;

    }
 
    public void setEndDate(LocalDate endDate) {

        this.endDate = endDate;

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

               ", user=" + (user != null ? user.getEmail() : "null") + // Use email for simplicity in toString

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

               '}';

    }

}

 