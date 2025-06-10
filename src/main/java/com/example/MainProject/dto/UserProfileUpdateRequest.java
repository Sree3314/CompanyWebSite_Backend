package com.example.MainProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class UserProfileUpdateRequest {
    @NotBlank(message = "First name cannot be empty")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Size(max = 100, message = "Contact information cannot exceed 100 characters")
    private String contactInformation;

    @Size(max = 50, message = "Department cannot exceed 50 characters")
    private String department;

    @Size(max = 50, message = "Job title cannot exceed 50 characters")
    private String jobTitle;

    @Size(max = 255, message = "Profile picture URL cannot exceed 255 characters")
    private String profilePictureUrl; // For updating or clearing

    // No-argument constructor
    public UserProfileUpdateRequest() {
    }

    // All-argument constructor
    public UserProfileUpdateRequest(String firstName, String lastName, String contactInformation, String department, String jobTitle, String profilePictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInformation = contactInformation;
        this.department = department;
        this.jobTitle = jobTitle;
        this.profilePictureUrl = profilePictureUrl;
    }

    // --- Getters ---
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getDepartment() {
        return department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    // --- Setters ---
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileUpdateRequest that = (UserProfileUpdateRequest) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(contactInformation, that.contactInformation) && Objects.equals(department, that.department) && Objects.equals(jobTitle, that.jobTitle) && Objects.equals(profilePictureUrl, that.profilePictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, contactInformation, department, jobTitle, profilePictureUrl);
    }

    @Override
    public String toString() {
        return "UserProfileUpdateRequest{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", contactInformation='" + contactInformation + '\'' +
               ", department='" + department + '\'' +
               ", jobTitle='" + jobTitle + '\'' +
               ", profilePictureUrl='" + profilePictureUrl + '\'' +
               '}';
    }
}
