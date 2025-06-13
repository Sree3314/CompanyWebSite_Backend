package com.example.MainProject.dto;

import java.util.Objects;

public class UserProfileResponse {
    private Long id;
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactInformation;
    private String department;
    private String jobTitle;
    private String profilePictureUrl;

    // No-argument constructor
    public UserProfileResponse() {
    }

    // All-argument constructor
    public UserProfileResponse( Long employeeId, String firstName, String lastName, String email, String contactInformation, String department, String jobTitle, String profilePictureUrl) {
  
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactInformation = contactInformation;
        this.department = department;
        this.jobTitle = jobTitle;
        this.profilePictureUrl = profilePictureUrl;
    }

   

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
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
 

    public void setEmployeeId(Long long1) {
        this.employeeId = long1;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
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
        UserProfileResponse that = (UserProfileResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
               " employeeId='" + employeeId + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", contactInformation='" + contactInformation + '\'' +
               ", department='" + department + '\'' +
               ", jobTitle='" + jobTitle + '\'' +
               ", profilePictureUrl='" + profilePictureUrl + '\'' +
               '}';
    }
}
