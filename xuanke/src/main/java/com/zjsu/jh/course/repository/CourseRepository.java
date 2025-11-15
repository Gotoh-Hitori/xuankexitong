package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Course;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CourseRepository {
    private final Map<String, Course> courseMap = new ConcurrentHashMap<>();

    public Course save(Course course) {
        if (course.getId() == null) {
            course.setId(UUID.randomUUID().toString());
        }
        courseMap.put(course.getId(), course);
        return course;
    }

    // ✅ 新增 update 方法，供 Service 调用
    public void update(Course course) {
        if (course == null || course.getId() == null) {
            throw new IllegalArgumentException("课程或课程ID不能为空");
        }
        courseMap.put(course.getId(), course);
    }

    public Optional<Course> findById(String id) {
        return Optional.ofNullable(courseMap.get(id));
    }

    public Optional<Course> findByCode(String code) {
        return courseMap.values().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public List<Course> findAll() {
        return new ArrayList<>(courseMap.values());
    }

    public void deleteById(String id) {
        courseMap.remove(id);
    }

    // ✅ 保留 clear 方法用于测试初始化
    public void clear() {
        courseMap.clear();
    }
}
