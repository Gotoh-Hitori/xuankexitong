package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    // 按学号查询
    Optional<Student> findByStudentId(String studentId);
    
    // 按邮箱查询
    Optional<Student> findByEmail(String email);
    
    // 判重检查
    boolean existsByStudentId(String studentId);
    
    boolean existsByEmail(String email);
    
    // 按专业筛选
    List<Student> findByMajor(String major);
    
    // 按年级筛选
    List<Student> findByGrade(Integer grade);
}
