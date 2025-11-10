package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.StudentDTO;
import com.example.student_management_system.model.Student;

public interface IStudentService {
	Student createStudent(StudentDTO student);
	Student getStudentById(Long id);
	List<Student> getAllStudents();
	Student updateStudent(Long id, StudentDTO student);
	void deleteStudent(Long id);
	void enrollStudentInCourse(Long studentId, Long courseId);
	void removeStudentFromCourse(Long studentId, Long courseId);
	

}
