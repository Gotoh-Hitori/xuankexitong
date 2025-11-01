package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.BusinessException;
import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public Course createCourse(Course course) {
        if (courseRepository.findByCode(course.getCode()).isPresent()) {
            throw new BusinessException("课程代码已存在：" + course.getCode());
        }
        course.setCreatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在 ID=" + id));
    }

    @Transactional
    public Course updateCourse(String id, Course updated) {
        Course existing = getCourseById(id);
        // 检查是否有其他课程使用了相同的代码
        if (!existing.getCode().equals(updated.getCode()) && 
            courseRepository.findByCode(updated.getCode()).isPresent()) {
            throw new BusinessException("课程代码已存在：" + updated.getCode());
        }
        
        existing.setCode(updated.getCode());
        existing.setTitle(updated.getTitle());
        existing.setInstructor(updated.getInstructor());
        existing.setSchedule(updated.getSchedule());
        existing.setCapacity(updated.getCapacity());
        return courseRepository.save(existing);
    }

    @Transactional
    public void deleteCourse(String id) {
        // 检查是否有关联的选课记录
        Course course = getCourseById(id);
        // 这里应该检查是否有活跃的选课记录，但为了简化，我们直接删除
        courseRepository.deleteById(id);
    }
    
    // 新增方法：按课程代码查询
    @Transactional(readOnly = true)
    public Course getCourseByCode(String code) {
        return courseRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在 code=" + code));
    }
    
    // 新增方法：按讲师编号查询
    @Transactional(readOnly = true)
    public List<Course> getCoursesByInstructorId(String instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    
    // 新增方法：筛选有剩余容量的课程
    @Transactional(readOnly = true)
    public List<Course> getCoursesWithAvailableCapacity() {
        return courseRepository.findCoursesWithAvailableCapacity();
    }
    
    // 新增方法：支持标题关键字模糊查询
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTitleKeyword(String titleKeyword) {
        return courseRepository.findByTitleContainingIgnoreCase(titleKeyword);
    }
}