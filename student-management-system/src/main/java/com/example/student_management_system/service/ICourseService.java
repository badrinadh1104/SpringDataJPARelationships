package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.CourseDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;

public interface ICourseService {
	Course saveCourse(CourseDTO course);
	
	Course getCourseById(Long id);
	
	List<Course> getAllCourses();
	
	Course updateCourse(Long id, CourseDTO course);
	
	void deleteCourse(Long id);
	
	List<Student> getEnrolledStudents(Long courseId);
	
	List<Teacher> getAssignedTeachers(Long courseId);
	
	void assignTeacherToCourse(Long courseId, Long teacherId);
	
	void removeTeacherFromCourse(Long courseId, Long teacherId);

}
