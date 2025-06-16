package com.example.MainProject.model;
 
import jakarta.persistence.*;
import java.util.Date;
 
@Entity
@Table(name = "applications")
public class Application {
 
	public enum Status {
		PENDING, ACCEPTED, DECLINED
	}
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;
 
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private User employee;
 
	private String resumeLink;
	private String skills;
	private int yearsOfExperience;
 
	@Enumerated(EnumType.STRING)
	private Status status;
 
	@Temporal(TemporalType.TIMESTAMP)
	private Date appliedDate;
 
	private String employeeName; // New field for full name
 
// Getters and Setters
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public Job getJob() {
		return job;
	}
 
	public void setJob(Job job) {
		this.job = job;
	}
 
	public User getEmployee() {
		return employee;
	}
 
	public void setEmployee(User employee) {
		this.employee = employee;
	}
 
	public String getResumeLink() {
		return resumeLink;
	}
 
	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}
 
	public String getSkills() {
		return skills;
	}
 
	public void setSkills(String skills) {
		this.skills = skills;
	}
 
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
 
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
 
	public Status getStatus() {
		return status;
	}
 
	public void setStatus(Status status) {
		this.status = status;
	}
 
	public Date getAppliedDate() {
		return appliedDate;
	}
 
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
 
	public String getEmployeeName() {
		return employeeName;
	}
 
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
}
 
 