package com.example.MainProject.service;

import com.example.MainProject.dto.EmployeeRatingPerformance;
import com.example.MainProject.dto.LeaderboardEntryResponse;
import com.example.MainProject.model.User;
import com.example.MainProject.model.Upload; // Assuming Upload model exists
import com.example.MainProject.repository.UserRepository;
import com.example.MainProject.repository.UploadRepository; // Inject UploadRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.OptionalDouble;

/**
 * Service for generating leaderboard data based on various metrics.
 */
@Service
public class LeaderboardService {

    private final UserRepository userRepository;
    private final UploadRepository uploadRepository; // Inject UploadRepository

    @Autowired
    public LeaderboardService(UserRepository userRepository, UploadRepository uploadRepository) {
        this.userRepository = userRepository;
        this.uploadRepository = uploadRepository;
    }

    /**
     * Retrieves a leaderboard of employees based on the number of projects uploaded.
     * The list is sorted in descending order of projects uploaded.
     * This method is kept for compatibility if you still need it, but the new focus is average rating.
     *
     * @return A list of LeaderboardEntryResponse objects.
     */
    public List<LeaderboardEntryResponse> getProjectsUploadedLeaderboard() {
        List<User> users = userRepository.findAll();

        users.sort(Comparator.comparingInt(User::getProjectsUploaded).reversed());

        return users.stream()
                .map(user -> new LeaderboardEntryResponse(
                        user.getEmployeeId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getProjectsUploaded()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a leaderboard of employees based on the overall average rating of their uploaded projects.
     * Only projects marked for showcase and approved, and that have a rating, are considered.
     * The list is sorted in descending order of average rating.
     *
     * @return A list of EmployeeRatingPerformanceDTO objects.
     */
    public List<EmployeeRatingPerformance> getAverageRatingLeaderboard() {
        // Fetch all approved showcase uploads that have a rating
        List<Upload> ratedShowcaseUploads = uploadRepository.findByIsForShowcaseTrueAndApprovalStatusAndRatingIsNotNull("approved");

        // Group uploads by the user who uploaded them
        Map<User, List<Upload>> uploadsByUser = ratedShowcaseUploads.stream()
                .filter(upload -> upload.getUser() != null) // Ensure user is linked
                .collect(Collectors.groupingBy(Upload::getUser));

        List<EmployeeRatingPerformance> leaderboard = uploadsByUser.entrySet().stream()
                .map(entry -> {
                    User uploader = entry.getKey();
                    List<Upload> userUploads = entry.getValue();

                    // Calculate average rating for this user's uploads
                    OptionalDouble averageRatingOptional = userUploads.stream()
                            .filter(upload -> upload.getRating() != null) // Ensure rating is not null
                            .mapToInt(Upload::getRating)
                            .average();

                    double averageRating = averageRatingOptional.orElse(0.0); // Default to 0.0 if no rated projects

                    return new EmployeeRatingPerformance(
                            uploader.getEmployeeId(),
                            uploader.getFirstName(),
                            uploader.getLastName(),
                            averageRating
                    );
                })
                .sorted(Comparator.comparingDouble(EmployeeRatingPerformance::getAverageRating).reversed()) // Sort by average rating descending
                .collect(Collectors.toList());

        return leaderboard;
    }

    /**
     * Helper method to increment an employee's projects uploaded count.
     * This method would be called by other services (e.g., a ProjectService)
     * when a new project is uploaded by a user.
     *
     * @param employeeId The ID of the employee.
     * @param incrementBy The amount to increment the projects uploaded by (usually 1).
     * @throws RuntimeException if the user is not found.
     */
    public void incrementProjectsUploaded(Long employeeId, int incrementBy) {
        User user = userRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + employeeId));
        user.setProjectsUploaded(user.getProjectsUploaded() + incrementBy);
        userRepository.save(user);
    }
}
