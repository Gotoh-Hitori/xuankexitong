package com.zjsu.jh.course.controller;

import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // 查询所有课程
    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", courses
        ));
    }

    // 查询单个课程
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", course
        ));
    }

    // 创建课程
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Course created = courseService.createCourse(course);
        return ResponseEntity.status(201).body(Map.of(
                "code", 201,
                "message", "Created",
                "data", created
        ));
    }

    // 更新课程
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody Course course) {
        Course updated = courseService.updateCourse(id, course);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", updated
        ));
    }

    // 删除课程
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
