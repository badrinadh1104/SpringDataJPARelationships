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

import com.example.student_management_system.DTO.TeacherDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.service.ITeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin
@RequestMapping("v1/student-management-system/teachers")
@Validated
@Tag(name = "Teacher Management", description = "APIs for managing teachers")
public class TeacherController {
	private ITeacherService teacherService;
	public TeacherController(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
	@PostMapping
	@Operation(summary = "Create a new teacher", description = "Creates a new teacher with the provided details.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Teacher created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	public ResponseEntity<Teacher> createTeacher(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "teacher details") @Valid @RequestBody TeacherDTO teacher) {
		return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(teacher));
	}
	
	@Operation(summary = "Get teacher by ID", description = "Retrieves a teacher by their unique ID.")	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Teacher retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Teacher not found") })	
	@GetMapping(value = "/{teacherId}")
	public ResponseEntity<Teacher> getTeacherById(@Parameter(description = "TeacherId is Required",required = true) @PathVariable(name = "teacherId") Long id) {
		return ResponseEntity.ok(teacherService.getTeacherById(id));
	}
	
	@Operation(summary = "Get all teachers", description = "Retrieves a list of all teachers.")	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Teachers retrieved successfully") })	
	@GetMapping
	public ResponseEntity<List<Teacher>> getAllTeachers() {
		return ResponseEntity.ok().body(teacherService.getAllTeachers());
	}
	
	@Operation(summary = "Update teacher details", description = "Updates the details of an existing teacher.")	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Teacher updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Teacher not found") })	
	@PutMapping("/{teacherId}")
	public ResponseEntity<Teacher> updateTeacher(@Parameter(description = "TeacherId is Required") @PathVariable(name = "teacherId") Long id,@RequestBody TeacherDTO teacher) {
		return ResponseEntity.ok(teacherService.updateTeacher(id, teacher));
	}
	
	@Operation(summary = "Delete a teacher", description = "Deletes an existing teacher by their unique ID.")	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "Teacher deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Teacher not found") })	
	@DeleteMapping("/{teacherId}")
	public ResponseEntity<Void> deleteTeacher(@Parameter(description = "TeacherId is Required") @PathVariable(name = "teacherId") Long id) {
		teacherService.deleteTeacher(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Get courses taught by a teacher", description = "Retrieves a list of courses taught by a specific teacher.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "Courses retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Teacher not found") })	
	@GetMapping("/{teacherId}/courses")
	public ResponseEntity<List<Course>> getCoursesTaught(@Parameter(description = "TeacherId is Required") @PathVariable(name = "teacherId") Long teacherId) {
		return ResponseEntity.ok(teacherService.getCoursesTaught(teacherId));
	}
	
	@Operation(summary = "Assign course to teacher", description = "Assigns a specific course to a teacher.")	
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Course assigned to teacher successfully"),
			@ApiResponse(responseCode = "404", description = "Teacher or Course not found") })	
	@PutMapping("/{teacherId}/courses/{courseId}")
	public ResponseEntity<Void> assignCourseToTeacher(@Parameter(description = "TeacherId is Required") @PathVariable(name = "teacherId") Long teacherId,
			@Parameter(description = "CourseId is Required") @PathVariable(name = "courseId") Long courseId) {
		teacherService.assignCourseToTeacher(teacherId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation(summary = "Remove course from teacher", description = "Removes a specific course from a teacher.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "Course removed from teacher successfully"),
			@ApiResponse(responseCode = "404", description = "Teacher or Course not found") })
	@PutMapping("/{teacherId}/courses/{courseId}/remove")
	public ResponseEntity<Void> removeCourseFromTeacher(@Parameter(description = "TeacherId is Required") @PathVariable(name = "teacherId") Long teacherId,
			@Parameter(description = "CourseId is Required") @PathVariable(name = "courseId") Long courseId) {
		teacherService.removeCourseFromTeacher(teacherId, courseId);
		return ResponseEntity.noContent().build();
	}
	
	
	

}
