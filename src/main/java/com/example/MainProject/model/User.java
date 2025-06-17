package com.example.MainProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	
	@Id
    @NotNull(message = "Employee ID is required")
    @Min(value = 1, message = "Employee ID must be a positive number")
    @Column(unique = true, nullable = false)
    private Long employeeId;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	private String contactInformation;

	private String department;

	private String jobTitle;

	private String profilePictureUrl; // Store URL/path to the image

	@Column(nullable = true) // Allow password_hash to be NULL for UNREGISTERED users initially
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountStatus accountStatus;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role; // Single role for simplicity

	// Fields for password reset/verification (personal email for recovery)
	// Kept for future non-OTP password reset, but not used in this phase's logic.
	@Column(unique = true, nullable = false) // Add unique = true here
	private String personalEmail;

	public enum AccountStatus {
		UNREGISTERED, // Employee ID exists, but hasn't completed registration/set password
		ACTIVE, // Account is fully active
		SUSPENDED, // Account temporarily suspended
		DEACTIVATED // Account permanently deactivated
	}

	public enum Role {
		USER, MANAGER
	}

	// No-argument constructor (required by JPA)
	public User() {
		this.role = Role.USER;
		this.accountStatus = AccountStatus.UNREGISTERED;
	}

	// --- Getters and Setters for all fields ---

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	// --- UserDetails implementations ---
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public String getPassword() {
		return this.passwordHash;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Account is enabled if ACTIVE. UNREGISTERED accounts are not enabled for
		// login.
		return this.accountStatus == AccountStatus.ACTIVE;
	}

	// --- toString(), equals(), hashCode() ---
	@Override
	public String toString() {
		return "User{" + " employeeId='" + employeeId + '\'' + ", firstName='" + firstName + '\'' + ", lastName='"
				+ lastName + '\'' + ", email='" + email + '\'' + ", contactInformation='" + contactInformation + '\''
				+ ", department='" + department + '\'' + ", jobTitle='" + jobTitle + '\'' + ", profilePictureUrl='"
				+ profilePictureUrl + '\'' + ", passwordHash='[PROTECTED]'" + ", accountStatus=" + accountStatus
				+ ", role=" + role + ", personalEmail='" + personalEmail + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User) o;
		return Objects.equals(employeeId, user.employeeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId);
	}
}
