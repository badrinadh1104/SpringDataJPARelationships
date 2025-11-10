package com.example.student_management_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.CourseDTO;
import com.example.student_management_system.exceptions.CourseNotFoundException;
import com.example.student_management_system.exceptions.TeacherNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repsitory.CourseRepository;
import com.example.student_management_system.repsitory.TeacherRepository;

@Service
public class CourseServiceImpl implements ICourseService {
	private CourseRepository courseRepository;
	private TeacherRepository teacherRepository;
	public CourseServiceImpl(CourseRepository courseRepository,TeacherRepository teacherRepository) {	
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
	}
	@Override
	public Course saveCourse(CourseDTO course) {
		// TODO Auto-generated method stub
		Course newCourse = Course.builder()
				.courseName(course.courseName())
				.courseDescription(course.courseDescription())
				.credits(course.credits())
				.build();
		return courseRepository.save(newCourse);
	}

	@Override
	public Course getCourseById(Long id) {
		return courseRepository.findById(id).orElseThrow(()-> new CourseNotFoundException("Course not found with id: " + id));
	}

	@Override
	public List<Course> getAllCourses() {
		// TODO Auto-generated method stub
		return courseRepository.findAll();
	}

	@Override
	public Course updateCourse(Long id, CourseDTO course) {
		Course existingCourse = courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
		existingCourse.setCourseName(course.courseName());
		existingCourse.setCourseDescription(course.courseDescription());
		existingCourse.setCredits(course.credits());
		return courseRepository.save(existingCourse);
		
	}

	@Override
	public void deleteCourse(Long id) {
		Course existingCourse = courseRepository.findById(id)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
		courseRepository.delete(existingCourse);
		
	}

	@Override
	public List<Student> getEnrolledStudents(Long courseId) {
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		return existingCourse.getStudents();
	}

	@Override
	public List<Teacher> getAssignedTeachers(Long courseId) {
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		return existingCourse.getTeachers();
	}

	@Override
	public void assignTeacherToCourse(Long courseId, Long teacherId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
		course.addTeacher(teacher);
		courseRepository.save(course);
	}

	@Override
	public void removeTeacherFromCourse(Long courseId, Long teacherId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
		course.removeTeacher(teacher);
		
	}
	
	
	

}
