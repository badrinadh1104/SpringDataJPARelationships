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

import com.example.student_management_system.DTO.CourseDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.service.CourseServiceImpl;
import com.example.student_management_system.service.ICourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@CrossOrigin
@RequestMapping("v1/student-management-system/courses")
@Validated
@Tag(name = "Courses", description = "Course management endpoints")
public class CourseController {

    private ICourseService courseService;

    public CourseController(CourseServiceImpl courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course with the provided details.")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data") })
    public ResponseEntity<Course> createCourse(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Course details") @Valid @RequestBody CourseDTO course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
    }

    @Operation(summary = "Get course by ID", description = "Retrieves a course by its unique ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Course retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found") })
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @Operation(summary = "Get all courses", description = "Retrieves a list of all courses.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Courses retrieved successfully") })
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @Operation(summary = "Update course details", description = "Updates the details of an existing course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data") })
    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated course details") @RequestBody CourseDTO course) {
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @Operation(summary = "Delete a course", description = "Deletes a course by its unique ID.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found") })
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get students enrolled in a course", description = "Retrieves students enrolled in the given course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Students retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found") })
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>> getEnrolledStudents(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
        return ResponseEntity.ok(courseService.getEnrolledStudents(courseId));
    }

    @Operation(summary = "Get teachers assigned to a course", description = "Retrieves teachers assigned to the given course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Teachers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found") })
    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<List<Teacher>> getAssignedTeachers(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId) {
        return ResponseEntity.ok(courseService.getAssignedTeachers(courseId));
    }

    @Operation(summary = "Assign a teacher to a course", description = "Assigns an existing teacher to the specified course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Teacher assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Course or Teacher not found") })
    @PutMapping("/{courseId}/teachers/{teacherId}")
    public ResponseEntity<Void> assignTeacherToCourse(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId,
            @Parameter(description = "Teacher Id Required") @PathVariable(name = "teacherId") Long teacherId) {
        courseService.assignTeacherToCourse(courseId, teacherId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Remove a teacher from a course", description = "Removes an assigned teacher from the specified course.")
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Teacher removed successfully"),
            @ApiResponse(responseCode = "404", description = "Course or Teacher not found") })
    @PutMapping("/{courseId}/teachers/{teacherId}/remove")
    public ResponseEntity<Void> removeTeacherFromCourse(
            @Parameter(description = "Course Id Required") @PathVariable(name = "courseId") Long courseId,
            @Parameter(description = "Teacher Id Required") @PathVariable(name = "teacherId") Long teacherId) {
        courseService.removeTeacherFromCourse(courseId, teacherId);
        return ResponseEntity.noContent().build();
    }

}