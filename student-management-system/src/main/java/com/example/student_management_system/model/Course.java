package com.example.student_management_system.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	@NotBlank(message = "Course name is required")
	private String courseName;
	@NotBlank(message = "Course description is required")
	private String courseDescription;
	@NotNull(message = "Credits are required")
	private int credits;
	@NotNull(message = "Start date is required")
	private LocalDate startDate;
	@NotNull(message = "End date is required")
	private LocalDate endDate;
	@JsonBackReference
	@ManyToMany(mappedBy = "courses")
	private List<Teacher> teachers;
	@JsonBackReference
	@ManyToMany(mappedBy = "courses")
	private List<Student> students;
	@JsonManagedReference
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "department_id")
	private Department department;
	
	@PrePersist
	private void setDates() {
		if(this.startDate == null) {
			this.startDate = LocalDate.now();
		}
		if(this.endDate == null) {
			this.endDate = this.startDate.plusMonths(4);
		}
	}
	
	public void addTeacher(Teacher teacher) {
		if(teachers == null) {
			teachers = new ArrayList<>();
		}
		teachers.add(teacher);
	}
	
	public void addStudent(Student student) {
		if(students == null) {
			students = new ArrayList<>();
		}
		students.add(student);
	}
	
	public void removeStudent(Student student) {
		if(students != null) {
			students.remove(student);
		}
	}
	public void removeTeacher(Teacher teacher) {
		if(teachers != null) {
			teachers.remove(teacher);
		}
	}

}
