package com.example.MainProject.controller;

import com.example.MainProject.dto.CommentRequest;
import com.example.MainProject.dto.RatingRequest;
import com.example.MainProject.dto.ShowcaseItemDTO;
import com.example.MainProject.service.ExhibitionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST Controller for the public exhibition of approved uploads and manager actions.
 */
@RestController
@RequestMapping("/api") // Root path for exhibition and manager actions
public class ExhibitionController {

    private final ExhibitionService exhibitionService;

    @Autowired
    public ExhibitionController(ExhibitionService exhibitionService) {
        this.exhibitionService = exhibitionService;
    }

    /**
     * Retrieves all uploads marked for showcase. This is a publicly accessible endpoint.
     * GET /api/exhibition
     * @return ResponseEntity with a list of ShowcaseItemDTOs and HttpStatus.OK.
     */
    @GetMapping("/exhibition")
    public ResponseEntity<List<ShowcaseItemDTO>> getShowcaseItems() {
        try {
            List<ShowcaseItemDTO> showcaseItems = exhibitionService.getAllShowcaseItems();
            return ResponseEntity.ok(showcaseItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Allows a MANAGER to rate an upload.
     * PATCH /api/uploads/{uploadId}/rate
     * Requires authentication with MANAGER role.
     * @param uploadId The ID of the upload to rate.
     * @param ratingRequest DTO containing the rating value (1-5).
     * @return ResponseEntity with HttpStatus.NO_CONTENT on success.
     */
    @PatchMapping("/uploads/{uploadId}/rate")
    public ResponseEntity<Void> rateUpload(@PathVariable Long uploadId, @Valid @RequestBody RatingRequest ratingRequest) {
        try {
            exhibitionService.rateUpload(uploadId, ratingRequest);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Allows a MANAGER to add a comment to an upload.
     * PATCH /api/uploads/{uploadId}/comment
     * Requires authentication with MANAGER role.
     * @param uploadId The ID of the upload to comment on.
     * @param commentRequest DTO containing the comment text.
     * @return ResponseEntity with HttpStatus.NO_CONTENT on success.
     */
    @PatchMapping("/uploads/{uploadId}/comment")
    public ResponseEntity<Void> commentUpload(@PathVariable Long uploadId, @Valid @RequestBody CommentRequest commentRequest) {
        try {
            exhibitionService.commentUpload(uploadId, commentRequest);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
