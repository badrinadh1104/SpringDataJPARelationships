package com.example.student_management_system.service;

import java.util.List;

import com.example.student_management_system.DTO.TeacherDTO;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Teacher;

public interface ITeacherService {
	Teacher createTeacher(TeacherDTO teacher);
	Teacher getTeacherById(Long id);
	List<Teacher> getAllTeachers();
	Teacher updateTeacher(Long id, TeacherDTO teacher);
	void deleteTeacher(Long id);
	List<Course> getCoursesTaught(Long teacherId);
	void assignCourseToTeacher(Long teacherId, Long courseId);
	void removeCourseFromTeacher(Long teacherId, Long courseId);

}
