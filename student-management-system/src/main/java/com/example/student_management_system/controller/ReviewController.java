package com.example.student_management_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student_management_system.DTO.ReviewDTO;
import com.example.student_management_system.model.Review;
import com.example.student_management_system.service.IReviewService;
import com.example.student_management_system.service.ReviewServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin
@RequestMapping("v1/student-management-system/reviews")
@Validated
@Tag(name = "Reviews", description = "Review management endpoints")
public class ReviewController {

    private final IReviewService reviewService;

    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a new review. If student/course/teacher IDs are provided they will be validated and linked.")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Related entity not found") })
    public ResponseEntity<Review> createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Review details") @Valid @RequestBody ReviewDTO review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(review));
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by ID", description = "Retrieves a review by its unique ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Review retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found") })
    public ResponseEntity<Review> getReviewById(
            @Parameter(description = "Review Id Required") @PathVariable(name = "reviewId") Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get reviews for a course", description = "Retrieves reviews for the specified course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found") })
    public ResponseEntity<List<Review>> getReviewsByCourseId(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
        return ResponseEntity.ok(reviewService.getReviewsByCourseId(courseId));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get reviews by student", description = "Retrieves reviews submitted by the specified student.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found") })
    public ResponseEntity<List<Review>> getReviewsByStudentId(
            @Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long studentId) {
        return ResponseEntity.ok(reviewService.getReviewsByStudentId(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get reviews by teacher", description = "Retrieves reviews associated with the specified teacher.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Teacher not found") })
    public ResponseEntity<List<Review>> getReviewsByTeacherId(
            @Parameter(description = "Teacher Id Required") @PathVariable(name = "teacherId") Long teacherId) {
        return ResponseEntity.ok(reviewService.getReviewByTeacherId(teacherId));
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update an existing review", description = "Updates review content and associations.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "404", description = "Review or related entity not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<Review> updateReview(
            @Parameter(description = "Review Id Required") @PathVariable(name = "reviewId") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated review details") @RequestBody ReviewDTO review) {
        return ResponseEntity.ok(reviewService.updateReview(id, review));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a review", description = "Deletes a review by its unique ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found") })
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "Review Id Required") @PathVariable(name = "reviewId") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}