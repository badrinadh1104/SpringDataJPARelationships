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

import com.example.student_management_system.DTO.StudentDTO;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.service.IStudentService;
import com.example.student_management_system.service.StudentServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin
@RequestMapping("v1/student-management-system/students")
@Validated
@Tag(name = "Students", description = "Student management endpoints")
public class StudentController {

	private IStudentService studentService;

	public StudentController(StudentServiceImpl studentService) {
		this.studentService = studentService;
	}

	@PostMapping
	@Operation(summary = "Create a new student", description = "Creates a new student with the provided details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Student created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	public ResponseEntity<Student> createStudent(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "student details") @Valid @RequestBody StudentDTO student) {
		return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
	}

	@Operation(summary = "Get student by ID", description = "Retrieves a student by their unique ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Student retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found") })
	@GetMapping("/{studentId}")
	public ResponseEntity<Student> getStudentById(
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long id) {
		return ResponseEntity.ok(studentService.getStudentById(id));
	}
	
	@Operation(summary = "Get all students", description = "Retrieves a list of all students.")	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Students retrieved successfully") })	
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudents() {
		return ResponseEntity.ok(studentService.getAllStudents());
	}
	
	@Operation(summary = "Update student details", description = "Updates the details of an existing student.")	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Student updated successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })	
	@PutMapping("/{studentId}")
	public ResponseEntity<Student> updateStudent(
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated student details") @RequestBody StudentDTO student) {
		return ResponseEntity.ok(studentService.updateStudent(id, student));
	}
	
	@Operation(summary = "Delete a student", description = "Deletes a student by their unique ID.")	
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Student not found") })
	@DeleteMapping("/{studentId}")
	public ResponseEntity<Void> deleteStudent(
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Enroll student in a course", description = "Enrolls a student in a specified course.")	
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Student enrolled in course successfully"),
			@ApiResponse(responseCode = "404", description = "Student or Course not found") })	
	@PutMapping("/{studentId}/courses/{courseId}")
	public ResponseEntity<Void> enrollStudentInCourse(
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long studentId,
			@Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
		studentService.enrollStudentInCourse(studentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Remove student from a course", description = "Removes a student from a specified course.")	
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Student removed from course successfully"),
			@ApiResponse(responseCode = "404", description = "Student or Course not found") })
	@PutMapping("/{studentId}/courses/{courseId}/remove")
	public ResponseEntity<Void> removeStudentFromCourse(
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long studentId,
			@Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
		studentService.removeStudentFromCourse(studentId, courseId);
		return ResponseEntity.noContent().build();
	}

}
