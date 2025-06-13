
 
package com.example.MainProject.dto;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
 
/**
 * DTO for user registration.
 * Role is now determined by job title in the backend.
 */
public class RegisterRequest {
    @NotBlank(message = "Employee ID is required")
    @Size(max = 50, message = "Employee ID cannot exceed 50 characters")
    private long employeeId;
 
    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;
 
    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;
 
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    // Pattern to enforce company domain structure, if desired (e.g., @companyhub.com)
    // @Pattern(regexp = ".*@companyhub\\.com$", message = "Email must be from @companyhub.com domain")
    private String email; // This is the organizational email
 
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password;
 
    @Size(max = 255, message = "Contact information cannot exceed 255 characters")
    private String contactInformation;
 
    @Size(max = 100, message = "Department cannot exceed 100 characters")
    private String department;
 
    @NotBlank(message = "Job title is required") // Job title is now crucial for role determination
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String jobTitle;
 
    @Email(message = "Personal email must be a valid email address")
    @Size(max = 255, message = "Personal email cannot exceed 255 characters")
    private String personalEmail; // This is the personal recovery email
 
    // No-argument constructor
    public RegisterRequest() {
    }
 
    // All-argument constructor (excluding role)
    public RegisterRequest(Long employeeId, String firstName, String lastName, String email, String password,
                           String contactInformation, String department, String jobTitle, String personalEmail) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactInformation = contactInformation;
        this.department = department;
        this.jobTitle = jobTitle;
        this.personalEmail = personalEmail;
    }
 
    // --- Getters ---
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
 
    public String getPassword() {
        return password;
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
 
    public String getPersonalEmail() {
        return personalEmail;
    }
 
    // --- Setters ---
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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
 
    public void setPassword(String password) {
        this.password = password;
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
 
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }
 
    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(employeeId, that.employeeId) &&
               Objects.equals(firstName, that.firstName) &&
               Objects.equals(lastName, that.lastName) &&
               Objects.equals(email, that.email) &&
               Objects.equals(password, that.password) &&
               Objects.equals(contactInformation, that.contactInformation) &&
               Objects.equals(department, that.department) &&
               Objects.equals(jobTitle, that.jobTitle) &&
               Objects.equals(personalEmail, that.personalEmail);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, email, password, contactInformation, department, jobTitle, personalEmail);
    }
 
    @Override
    public String toString() {
        return "RegisterRequest{" +
               "employeeId='" + employeeId + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", contactInformation='" + contactInformation + '\'' +
               ", department='" + department + '\'' +
               ", jobTitle='" + jobTitle + '\'' +
               ", personalEmail='" + personalEmail + '\'' +
               '}';
    }
}
 
 