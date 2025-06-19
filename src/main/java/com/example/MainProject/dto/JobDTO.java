package com.example.MainProject.dto;
 
import jakarta.validation.constraints.NotBlank; // For String fields that cannot be null or empty/whitespace
import jakarta.validation.constraints.NotNull;  // For fields that cannot be null
import jakarta.validation.constraints.Size;    // For String length constraints
 
public class JobDTO {
    @NotBlank(message = "Job title cannot be empty")
    @Size(max = 255, message = "Job title cannot exceed 255 characters")
    private String title;
 
    @NotBlank(message = "Job description cannot be empty")
    @Size(max = 2000, message = "Job description cannot exceed 2000 characters") // Example max size
    private String description;
 
    @NotBlank(message = "Job location cannot be empty")
    @Size(max = 100, message = "Job location cannot exceed 100 characters")
    private String location;
 
    @NotBlank(message = "Salary cannot be empty") // Assuming salary is mandatory for a job posting
    @Size(max = 100, message = "Salary information cannot exceed 100 characters")
    private String salary;
 
    @NotBlank(message = "Job type cannot be empty")
    @Size(max = 50, message = "Job type cannot exceed 50 characters")
    private String jobType;
 
    @NotBlank(message = "Experience level cannot be empty")
    @Size(max = 50, message = "Experience level cannot exceed 50 characters")
    private String experienceLevel;
 
    @NotBlank(message = "Skills required cannot be empty")
    @Size(max = 500, message = "Skills required cannot exceed 500 characters")
    private String skillsRequired;
 
    @NotNull(message = "Manager ID cannot be null") // ManagerId is crucial for job ownership
    private Long managerId;
 
    // Getters and Setters (unchanged)
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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getJobType() {
        return jobType;
    }
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    public String getExperienceLevel() {
        return experienceLevel;
    }
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    public String getSkillsRequired() {
        return skillsRequired;
    }
    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }
    public Long getManagerId() {
        return managerId;
    }
    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }
}
 
 