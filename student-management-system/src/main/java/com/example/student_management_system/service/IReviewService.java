package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.ReviewDTO;
import com.example.student_management_system.model.Review;

public interface IReviewService {
	Review createReview(ReviewDTO review);
	Review getReviewById(Long id);
	List<Review> getReviewsByCourseId(Long courseId);
	List<Review> getReviewsByStudentId(Long studentId);
	List<Review> getReviewByTeacherId(Long teacherId);
	Review updateReview(Long id, ReviewDTO review);
	void deleteReview(Long id);

}
