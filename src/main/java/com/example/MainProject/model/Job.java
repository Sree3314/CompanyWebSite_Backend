package com.example.MainProject.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String location;
    private String salary;
    private String jobType;
    private String experienceLevel;
    private String skillsRequired;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Job() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public Date getPostedDate() {
        return postedDate;
    }
    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }
    public User getManager() {
        return manager;
    }
    public void setManager(User manager) {
        this.manager = manager;
    }
}
