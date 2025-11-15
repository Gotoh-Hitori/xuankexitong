package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        if (courseRepository.findByCode(course.getCode()).isPresent()) {
            throw new RuntimeException("课程代码已存在：" + course.getCode());
        }
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在 ID=" + id));
    }

    public Course updateCourse(String id, Course updated) {
        Course existing = getCourseById(id);
        existing.setTitle(updated.getTitle());
        existing.setInstructor(updated.getInstructor());
        existing.setSchedule(updated.getSchedule());
        existing.setCapacity(updated.getCapacity());
        return courseRepository.save(existing);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }
}
