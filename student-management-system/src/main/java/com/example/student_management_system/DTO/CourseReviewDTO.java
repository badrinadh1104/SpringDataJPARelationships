package com.example.student_management_system.DTO;

public record CourseReviewDTO(
		int rating,
		String comment,
		long studentId,
		long courseId
		) {
	

}
