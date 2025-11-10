package com.example.student_management_system.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.student_management_system.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCourseCourseId(Long courseId);
    List<Review> findByStudentStudentId(Long studentId);
    List<Review> findByTeacherTeacherId(Long teacherId);
}