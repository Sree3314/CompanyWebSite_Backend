package com.example.MainProject.dto;

/**
 * DTO for representing an employee's performance in terms of average project rating for the leaderboard.
 */
public class EmployeeRatingPerformance {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private double averageRating;

    // Constructors
    public EmployeeRatingPerformance() {
    }

    public EmployeeRatingPerformance(Long employeeId, String firstName, String lastName, double averageRating) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageRating = averageRating;
    }

    // Getters
    public Long getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    // Setters
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "EmployeeRatingPerformanceDTO{" +
               "employeeId=" + employeeId +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", averageRating=" + averageRating +
               '}';
    }
}
