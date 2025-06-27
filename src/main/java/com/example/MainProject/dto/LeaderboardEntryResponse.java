package com.example.MainProject.dto;

/**
 * DTO for representing an employee's entry in a leaderboard, now specifically
 * designed to hold average project ratings.
 */
public class LeaderboardEntryResponse {
    private Long employeeId;
    private String firstName;
    private String lastName;
    // This field now represents the average rating of an employee's projects
    private double averageRating;

    // No-argument constructor (required by some frameworks like Spring)
    public LeaderboardEntryResponse() {
    }

    /**
     * Parameterized constructor to initialize a LeaderboardEntryResponse object.
     *
     * @param employeeId The unique ID of the employee.
     * @param firstName The first name of the employee.
     * @param lastName The last name of the employee.
     * @param averageRating The average rating of the employee's projects.
     */
    public LeaderboardEntryResponse(Long employeeId, String firstName, String lastName, double averageRating) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.averageRating = averageRating;
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

    public double getAverageRating() {
        return averageRating;
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

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    // --- toString() for easy logging/debugging ---
    @Override
    public String toString() {
        return "LeaderboardEntryResponse{" +
               "employeeId=" + employeeId +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", averageRating=" + averageRating +
               '}';
    }
}
