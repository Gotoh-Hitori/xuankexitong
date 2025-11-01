package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    
    // 按课程代码查询
    Optional<Course> findByCode(String code);
    
    // 按讲师编号查询
    @Query("SELECT c FROM Course c WHERE c.instructor.id = ?1")
    List<Course> findByInstructorId(String instructorId);
    
    // 筛选有剩余容量的课程
    @Query("SELECT c FROM Course c WHERE c.enrolled < c.capacity")
    List<Course> findCoursesWithAvailableCapacity();
    
    // 支持标题关键字模糊查询
    List<Course> findByTitleContainingIgnoreCase(String titleKeyword);
}
