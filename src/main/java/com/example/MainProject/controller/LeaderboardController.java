package com.example.MainProject.controller;

import com.example.MainProject.dto.EmployeeRatingPerformance;
import com.example.MainProject.dto.LeaderboardEntryResponse;
import com.example.MainProject.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for retrieving leaderboard data.
 */
@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    /**
     * Retrieves the leaderboard for projects uploaded by employees.
     * GET /api/leaderboard/projects-uploaded
     * Requires authentication (e.g., any authenticated user can view the leaderboard).
     *
     * @return ResponseEntity with a list of LeaderboardEntryResponse and HttpStatus.OK.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/projects-uploaded")
    public ResponseEntity<List<LeaderboardEntryResponse>> getProjectsUploadedLeaderboard() {
        List<LeaderboardEntryResponse> leaderboard = leaderboardService.getProjectsUploadedLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    /**
     * NEW: Retrieves the leaderboard based on the average rating of employees' projects.
     * GET /api/leaderboard/average-ratings
     * Requires authentication (e.g., any authenticated user can view the leaderboard).
     *
     * @return ResponseEntity with a list of EmployeeRatingPerformanceDTO and HttpStatus.OK.
     */
    @PreAuthorize("isAuthenticated()") // Or "hasAnyRole('USER', 'MANAGER')"
    @GetMapping("/average-ratings")
    public ResponseEntity<List<EmployeeRatingPerformance>> getAverageRatingLeaderboard() {
        List<EmployeeRatingPerformance> leaderboard = leaderboardService.getAverageRatingLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
