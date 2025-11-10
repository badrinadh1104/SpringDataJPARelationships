package com.example.student_management_system.DTO;

public record ReviewDTO(
		int rating,
		String comment,
		Long studentId,
		Long teacherId,
		Long courseId
		) {
	

}
