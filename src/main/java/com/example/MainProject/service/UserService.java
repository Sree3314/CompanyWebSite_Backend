package com.example.MainProject.service;

import com.example.MainProject.dto.UserProfileResponse;
import com.example.MainProject.dto.UserProfileUpdateRequest;
import com.example.MainProject.model.User;
import com.example.MainProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing user profiles (getting and updating).
 * Operates on the unified User entity and UserProfileResponse/UpdateRequest DTOs.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Public getter for UserRepository, used by UserController's getAuthenticatedUserId fallback
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * Retrieves the profile of a user by their ID.
     * @param userId The ID of the user.
     * @return UserProfileResponse containing the user's profile details.
     * @throws RuntimeException if user not found.
     */
    public UserProfileResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        UserProfileResponse response = new UserProfileResponse();
        response.setEmployeeId(user.getEmployeeId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setContactInformation(user.getContactInformation());
        response.setDepartment(user.getDepartment());
        response.setJobTitle(user.getJobTitle());
        response.setProfilePictureUrl(user.getProfilePictureUrl());
     
        return response;
    }

    /**
     * Updates the profile of a user.
     * @param userId The ID of the user whose profile is to be updated.
     * @param updateRequest UserProfileUpdateRequest DTO with fields to update.
     * @return UserProfileResponse containing the updated user profile details.
     * @throws RuntimeException if user not found.
     */
    @Transactional
    public UserProfileResponse updateUserProfile(Long userId, UserProfileUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Update editable fields
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setContactInformation(updateRequest.getContactInformation());
        user.setDepartment(updateRequest.getDepartment());
        user.setJobTitle(updateRequest.getJobTitle());
        user.setProfilePictureUrl(updateRequest.getProfilePictureUrl());

        User updatedUser = userRepository.save(user);

        UserProfileResponse response = new UserProfileResponse();
        response.setEmployeeId(updatedUser.getEmployeeId());
        response.setFirstName(updatedUser.getFirstName());
        response.setLastName(updatedUser.getLastName());
        response.setEmail(updatedUser.getEmail());
        response.setContactInformation(updatedUser.getContactInformation());
        response.setDepartment(updatedUser.getDepartment());
        response.setJobTitle(updatedUser.getJobTitle());
        response.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
        return response;
    }
    
}
