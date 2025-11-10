package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.TeacherDTO;
import com.example.student_management_system.exceptions.CourseNotFoundException;
import com.example.student_management_system.exceptions.DuplicateEntryException;
import com.example.student_management_system.exceptions.TeacherNotFoundException;
import com.example.student_management_system.model.Address;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repsitory.CourseRepository;
import com.example.student_management_system.repsitory.TeacherRepository;

@Service
public class TeacherServiceImpl implements ITeacherService {
	private TeacherRepository teacherRepository;
	private CourseRepository courseRepository;
	@Autowired
	public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository) {
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public Teacher createTeacher(TeacherDTO teacher) {
		Optional<Teacher> existingTeacher = teacherRepository.findByEmail(teacher.email());
		if (existingTeacher.isPresent()) {
			throw new DuplicateEntryException("Teacher with email " + teacher.email() + " already exists.");
		}
		Address address = Address.builder()
				.street(teacher.addressDTO().street())
				.city(teacher.addressDTO().city())
				.state(teacher.addressDTO().state())
				.zipCode(teacher.addressDTO().zipCode())
				.country(teacher.addressDTO().country())
				.build();
		Teacher newTeacher = Teacher.builder()
				.firstName(teacher.firstName())
				.lastName(teacher.lastName())
				.email(teacher.email())
				.phoneNumber(teacher.phoneNumber())
				.speciality(teacher.specality())
				.active(teacher.active())
				.address(address)
				.build();
		return teacherRepository.save(newTeacher);
	}

	@Override
	public Teacher getTeacherById(Long id) {
		return teacherRepository.findById(id).orElseThrow(()-> new TeacherNotFoundException("Teacher not found with id: " + id));
	}

	@Override
	public List<Teacher> getAllTeachers() {
		return teacherRepository.findAll();
	}

	@Override
	public Teacher updateTeacher(Long id, TeacherDTO teacher) {
		Teacher existingTeacher = teacherRepository.findById(id)
				.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
		existingTeacher.setFirstName(teacher.firstName());
		existingTeacher.setLastName(teacher.lastName());
		existingTeacher.setEmail(teacher.email());
		existingTeacher.setPhoneNumber(teacher.phoneNumber());
		existingTeacher.setSpeciality(teacher.specality());
		existingTeacher.setActive(teacher.active());
		existingTeacher.getAddress().setStreet(teacher.addressDTO().street());
		existingTeacher.getAddress().setCity(teacher.addressDTO().city());
		existingTeacher.getAddress().setState(teacher.addressDTO().state());
		existingTeacher.getAddress().setZipCode(teacher.addressDTO().zipCode());
		existingTeacher.getAddress().setCountry(teacher.addressDTO().country());
		
		
		return teacherRepository.save(existingTeacher);
	}

	@Override
	public void deleteTeacher(Long id) {
		Teacher existingTeacher = teacherRepository.findById(id)
				.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
		teacherRepository.delete(existingTeacher);
		
	}

	@Override
	public List<Course> getCoursesTaught(Long teacherId) {
		Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
		return existingTeacher.getCourses();
	}

	@Override
	public void assignCourseToTeacher(Long teacherId, Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));	
		teacher.getCourses().add(course);
		teacherRepository.save(teacher);
		
		
	}

	@Override
	public void removeCourseFromTeacher(Long teacherId, Long courseId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));	
		teacher.getCourses().remove(course);
		teacherRepository.save(teacher);
		
	}
	
	

}
