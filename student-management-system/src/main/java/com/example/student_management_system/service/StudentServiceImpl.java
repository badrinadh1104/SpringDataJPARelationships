package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.StudentDTO;
import com.example.student_management_system.exceptions.CourseNotFoundException;
import com.example.student_management_system.exceptions.DuplicateEntryException;
import com.example.student_management_system.exceptions.StudentNotFoundException;
import com.example.student_management_system.model.Address;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repsitory.CourseRepository;
import com.example.student_management_system.repsitory.StudentRepository;

@Service
public class StudentServiceImpl implements IStudentService {
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public Student createStudent(StudentDTO student) {
		Optional<Student> existingStudent = studentRepository.findByEmail(student.email());
		if (existingStudent.isPresent()) {
			throw new DuplicateEntryException("Student with email " + student.email() + " already exists.");
		}
		Address address = Address.builder().street(student.addressDTO().street()).city(student.addressDTO().city())
				.state(student.addressDTO().state()).zipCode(student.addressDTO().zipCode())
				.country(student.addressDTO().country()).build();
		Student newStudent = Student.builder().firstName(student.firstName()).lastName(student.lastName())
				.email(student.email()).dateOfBirth(student.dateOfBirth()).gender(student.gender())
				.active(student.active()).phoneNumber(student.phoneNumber()).address(address).build();
		
		return studentRepository.save(newStudent);
	}

	@Override
	public Student getStudentById(Long id) {
		return studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student updateStudent(Long id, StudentDTO student) {
		Student existingStudent = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
		existingStudent.setFirstName(student.firstName());
		existingStudent.setLastName(student.lastName());
		existingStudent.setEmail(student.email());
		existingStudent.setDateOfBirth(student.dateOfBirth());
		existingStudent.setGender(student.gender());
		existingStudent.setActive(student.active());
		existingStudent.setPhoneNumber(student.phoneNumber());
		Address address = existingStudent.getAddress();
		address.setStreet(student.addressDTO().street());
		address.setCity(student.addressDTO().city());
		address.setState(student.addressDTO().state());
		address.setZipCode(student.addressDTO().zipCode());
		address.setCountry(student.addressDTO().country());
		existingStudent.setAddress(address);
		return studentRepository.save(existingStudent);

	}

	@Override
	public void deleteStudent(Long id) {
		Student existingStudent = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
		studentRepository.delete(existingStudent);

	}

	@Override
	public void enrollStudentInCourse(Long studentId, Long courseId) {
		Course course = courseRepository.findById(courseId).orElseThrow(
				() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Student student = studentRepository.findById(studentId).orElseThrow(
				() -> new StudentNotFoundException("Student not found with id: " + studentId));
		student.enrollInCourse(course);
		studentRepository.save(student);
	}

	@Override
	public void removeStudentFromCourse(Long studentId, Long courseId) {
		Course course = courseRepository.findById(courseId).orElseThrow(
				() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Student student = studentRepository.findById(studentId).orElseThrow(
				() -> new StudentNotFoundException("Student not found with id: " + studentId));
		student.removeFromCourse(course);
		studentRepository.save(student);

	}

}
