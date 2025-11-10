package com.example.student_management_system.repsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.student_management_system.model.Department;
import com.example.student_management_system.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	Optional<Teacher> findByEmail(String email);

	@Modifying
	@Query("UPDATE Teacher t SET t.department = :department WHERE t.teacherId = :teacherId")
	int updateDepartmentForTeacher(@Param("teacherId") Long teacherId, @Param("department") Department department);

	@Modifying
	@Query(value = "UPDATE teacher SET department_id = :deptId WHERE teacher_id = :teacherId", nativeQuery = true)
	int updateDepartmentForTeacherNative(@Param("teacherId") Long teacherId, @Param("deptId") Long deptId);

}