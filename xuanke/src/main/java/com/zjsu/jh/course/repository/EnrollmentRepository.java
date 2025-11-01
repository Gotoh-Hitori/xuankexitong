package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Enrollment;
import com.zjsu.jh.course.model.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    
    // 按课程、学生组合查询
    Optional<Enrollment> findByCourseIdAndStudentId(String courseId, String studentId);
    
    // 按课程、学生、状态组合查询
    Optional<Enrollment> findByCourseIdAndStudentIdAndStatus(String courseId, String studentId, EnrollmentStatus status);
    
    // 按课程查询
    List<Enrollment> findByCourseId(String courseId);
    
    // 按学生查询
    List<Enrollment> findByStudentId(String studentId);
    
    // 按课程和状态查询
    List<Enrollment> findByCourseIdAndStatus(String courseId, EnrollmentStatus status);
    
    // 按学生和状态查询
    List<Enrollment> findByStudentIdAndStatus(String studentId, EnrollmentStatus status);
    
    // 统计课程活跃人数
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseId = ?1 AND e.status = 'ACTIVE'")
    long countActiveEnrollmentsByCourseId(String courseId);
    
    // 判断学生是否已选课
    boolean existsByStudentIdAndCourseIdAndStatus(String studentId, String courseId, EnrollmentStatus status);
}
