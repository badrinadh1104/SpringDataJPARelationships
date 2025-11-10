package com.example.student_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.ReviewDTO;
import com.example.student_management_system.exceptions.CourseNotFoundException;
import com.example.student_management_system.exceptions.ReviewNotFoundException;
import com.example.student_management_system.exceptions.StudentNotFoundException;
import com.example.student_management_system.exceptions.TeacherNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Review;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repsitory.CourseRepository;
import com.example.student_management_system.repsitory.ReviewRepository;
import com.example.student_management_system.repsitory.StudentRepository;
import com.example.student_management_system.repsitory.TeacherRepository;

@Service
public class ReviewServiceImpl implements IReviewService {

	private final ReviewRepository reviewRepository;
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository, CourseRepository courseRepository,
			StudentRepository studentRepository, TeacherRepository teacherRepository) {
		this.reviewRepository = reviewRepository;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public Review createReview(ReviewDTO review) {

		// If related entities are provided, ensure they exist and attach managed
		// instances
		Review newReview = new Review();
		newReview.setRating(review.rating());
		newReview.setComment(review.comment());

		if (review.courseId() != null) {
			Long courseId = review.courseId();
			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
			newReview.setCourse(course);

		}
		if (review.studentId() != null) {
			Long studentId = review.studentId();
			Student student = studentRepository.findById(studentId)
					.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
			newReview.setStudent(student);
		}
		if (review.teacherId() != null) {
			Long teacherId = review.teacherId();
			Teacher teacher = teacherRepository.findById(teacherId)
					.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
			newReview.setTeacher(teacher);
		}
		return reviewRepository.save(newReview);
	}

	@Override
	public Review getReviewById(Long id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
	}

	@Override
	public List<Review> getReviewsByCourseId(Long courseId) {
		// Verify course exists
		courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		return reviewRepository.findByCourseCourseId(courseId);
	}

	@Override
	public List<Review> getReviewsByStudentId(Long studentId) {
		// Verify student exists
		studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
		return reviewRepository.findByStudentStudentId(studentId);
	}

	@Override
	public List<Review> getReviewByTeacherId(Long teacherId) {
		// Verify teacher exists
		teacherRepository.findById(teacherId)
				.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
		return reviewRepository.findByTeacherTeacherId(teacherId);
	}

	@Override
	public Review updateReview(Long id, ReviewDTO review) {
		Review existing = reviewRepository.findById(id)
				.orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
		existing.setRating(review.rating());
		existing.setComment(review.comment());
		// For associations, if provided, verify existence and set
		if (review.courseId() != null) {
			Long courseId = review.courseId();
			Course course = courseRepository.findById(courseId)
					.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
			existing.setCourse(course);
		}
		if (review.studentId() != null) {
			Long studentId = review.studentId();
			Student student = studentRepository.findById(studentId)
					.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
			existing.setStudent(student);
		}
		if (review.teacherId() != null) {
			Long teacherId = review.teacherId();
			Teacher teacher = teacherRepository.findById(teacherId)
					.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
			existing.setTeacher(teacher);
		}
		return reviewRepository.save(existing);
	}

	@Override
	public void deleteReview(Long id) {
		Review existing = reviewRepository.findById(id)
				.orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
		reviewRepository.delete(existing);
	}

}