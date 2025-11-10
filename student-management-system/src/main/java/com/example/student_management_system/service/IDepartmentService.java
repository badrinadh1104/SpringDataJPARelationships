package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.DepartmentDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Department;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;

public interface IDepartmentService {

	Department createDepartment(DepartmentDTO department);

	Department getDepartmentById(Long id);

	List<Department> getAllDepartments();

	Department updateDepartment(Long id, DepartmentDTO department);

	void deleteDepartment(Long id);

	List<Course> getCoursesInDepartment(Long departmentId);

	List<Student> getStudentsInDepartment(Long departmentId);

	List<Teacher> getTeachersInDepartment(Long departmentId);

	void assignTeacherToDepartment(Long teacherId, Long departmentId);

	void assignStudentToDepartment(Long studentId, Long departmentId);

	void assignCourseToDepartment(Long courseId, Long departmentId);

}
