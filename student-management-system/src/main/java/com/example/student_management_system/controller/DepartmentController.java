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

import com.example.student_management_system.DTO.DepartmentDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Department;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.service.DepartmentServiceImpl;
import com.example.student_management_system.service.IDepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin
@RequestMapping("v1/student-management-system/departments")
@Validated
@Tag(name = "Departments", description = "Department management endpoints")
public class DepartmentController {

	private IDepartmentService departmentService;

	public DepartmentController(DepartmentServiceImpl departmentService) {
		this.departmentService = departmentService;
	}

	@PostMapping
	@Operation(summary = "Create a new department", description = "Creates a new department with the provided details.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Department created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	public ResponseEntity<Department> createDepartment(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Department details") @Valid @RequestBody DepartmentDTO department) {
		return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.createDepartment(department));
	}

	@GetMapping("/{departmentId}")
	@Operation(summary = "Get department by ID", description = "Retrieves a department by its unique ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Department retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public ResponseEntity<Department> getDepartmentById(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long id) {
		return ResponseEntity.ok(departmentService.getDepartmentById(id));
	}

	@GetMapping
	@Operation(summary = "Get all departments", description = "Retrieves a list of all departments.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Departments retrieved successfully") })
	public ResponseEntity<List<Department>> getAllDepartments() {
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}

	@PutMapping("/{departmentId}")
	@Operation(summary = "Update department details", description = "Updates the details of an existing department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Department updated successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found"),
			@ApiResponse(responseCode = "400", description = "Invalid input data") })
	public ResponseEntity<Department> updateDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated department details") @RequestBody DepartmentDTO department) {
		return ResponseEntity.ok(departmentService.updateDepartment(id, department));
	}

	@DeleteMapping("/{departmentId}")
	@Operation(summary = "Delete a department", description = "Deletes a department by its unique ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Department deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public ResponseEntity<Void> deleteDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{departmentId}/courses")
	@Operation(summary = "Get courses in a department", description = "Retrieves courses that belong to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Courses retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public ResponseEntity<List<Course>> getCoursesInDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId) {
		return ResponseEntity.ok(departmentService.getCoursesInDepartment(departmentId));
	}

	@GetMapping("/{departmentId}/students")
	@Operation(summary = "Get students in a department", description = "Retrieves students that belong to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Students retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public ResponseEntity<List<Student>> getStudentsInDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId) {
		return ResponseEntity.ok(departmentService.getStudentsInDepartment(departmentId));
	}

	@GetMapping("/{departmentId}/teachers")
	@Operation(summary = "Get teachers in a department", description = "Retrieves teachers that belong to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Teachers retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "Department not found") })
	public ResponseEntity<List<Teacher>> getTeachersInDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId) {
		return ResponseEntity.ok(departmentService.getTeachersInDepartment(departmentId));
	}

	@PutMapping("/{departmentId}/teachers/{teacherId}")
	@Operation(summary = "Assign a teacher to a department", description = "Assigns an existing teacher to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Teacher assigned successfully"),
			@ApiResponse(responseCode = "404", description = "Department or Teacher not found") })
	public ResponseEntity<Void> assignTeacherToDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId,
			@Parameter(description = "Teacher Id Required") @PathVariable(name = "teacherId") Long teacherId) {
		departmentService.assignTeacherToDepartment(departmentId, teacherId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{departmentId}/courses/{courseId}")
	@Operation(summary = "Assign a course to a department", description = "Assigns an existing course to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Course assigned successfully"),
			@ApiResponse(responseCode = "404", description = "Department or Course not found") })
	public ResponseEntity<Void> assignCourseToDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId,
			@Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
		departmentService.assignCourseToDepartment(departmentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@PutMapping("/{departmentId}/students/{studentId}")
	@Operation(summary = "Assign a student to a department", description = "Assigns an existing student to the specified department.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Student assigned successfully"),
			@ApiResponse(responseCode = "404", description = "Department or Student not found") })
	public ResponseEntity<Void> assignStudentToDepartment(
			@Parameter(description = "Department Id Required") @PathVariable(name = "departmentId") Long departmentId,
			@Parameter(description = "Student Id Required") @PathVariable(name = "studentId") Long studentId) {
		departmentService.assignStudentToDepartment(departmentId, studentId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}