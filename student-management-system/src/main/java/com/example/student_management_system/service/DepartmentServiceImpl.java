package com.example.student_management_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.student_management_system.DTO.DepartmentDTO;
import com.example.student_management_system.exceptions.CourseNotFoundException;
import com.example.student_management_system.exceptions.DepartmentNotFoundException;
import com.example.student_management_system.exceptions.DuplicateEntryException;
import com.example.student_management_system.exceptions.StudentNotFoundException;
import com.example.student_management_system.exceptions.TeacherNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Department;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.model.Teacher;
import com.example.student_management_system.repsitory.CourseRepository;
import com.example.student_management_system.repsitory.DepartmentRepository;
import com.example.student_management_system.repsitory.StudentRepository;
import com.example.student_management_system.repsitory.TeacherRepository;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	private DepartmentRepository departmentRepository;
	private TeacherRepository teacherRepository;
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.departmentRepository = departmentRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;

	}

	@Override
	public Department createDepartment(DepartmentDTO department) {
		Optional<Department> existingDepartment = departmentRepository
				.findByDepartmentCode(department.departmentCode());
		if (existingDepartment.isPresent()) {
			throw new DuplicateEntryException(
					"Department with code " + department.departmentCode() + " already exists.");
		}
		Department newDepartment = Department.builder().departmentName(department.departmentName())
				.departmentCode(department.departmentCode()).description(department.description()).build();
		return departmentRepository.save(newDepartment);
	}

	@Override
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
	}

	@Override
	public List<Department> getAllDepartments() {

		return departmentRepository.findAll();
	}

	@Override
	public Department updateDepartment(Long id, DepartmentDTO department) {
		Department existingDepartment = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
		Department updatedDepartment = Department.builder().departmentId(existingDepartment.getDepartmentId())
				.departmentName(department.departmentName()).departmentCode(department.departmentCode())
				.description(department.description()).build();
		return departmentRepository.save(updatedDepartment);
	}

	@Override
	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);

	}

	@Override
	public List<Course> getCoursesInDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
		return department.getCourses();
	}

	@Override
	public List<Student> getStudentsInDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
		return department.getStudents();
	}

	@Override
	public List<Teacher> getTeachersInDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
		return department.getTeachers();
	}

	@Override
	public void assignTeacherToDepartment(Long teacherId, Long departmentId) {
		Teacher teacher = teacherRepository.findById(teacherId)
				.orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + teacherId));
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + departmentId));
		teacher.setDepartment(department);
		teacherRepository.save(teacher);

	}

	@Override
	public void assignStudentToDepartment(Long studentId, Long departmentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentId));
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + departmentId));
		student.setDepartment(department);
		studentRepository.save(student);

	}

	@Override
	public void assignCourseToDepartment(Long courseId, Long departmentId) {
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + departmentId));
		course.setDepartment(department);
		courseRepository.save(course);

	}

}
