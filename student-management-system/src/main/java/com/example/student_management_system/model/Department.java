package com.example.student_management_system.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;
	@NotBlank(message = "Department name is required")
	private String departmentName;
	@NotBlank(message = "Department code is required")
	private String departmentCode;
	@NotBlank(message = "Department description is required")
	private String description;
	@JsonBackReference
	@OneToMany(mappedBy = "department")
	private List<Course> courses;
	@JsonBackReference
	@OneToMany(mappedBy = "department")
	private List<Teacher> teachers;
	@JsonBackReference
	@OneToMany(mappedBy = "department")
	private List<Student> students;
	
	public void addCourse(Course course) {
		if(this.courses == null) {
			this.courses = new ArrayList<>();
		}
		this.courses.add(course);
	}
	public void addTeacher(Teacher teacher) {
		if(this.teachers == null) {
			this.teachers = new ArrayList<>();
		}
		this.teachers.add(teacher);
	}
	public void addStudent(Student student) {
		if(this.students == null) {
			this.students = new ArrayList<>();
		}
		this.students.add(student);
	}

}
