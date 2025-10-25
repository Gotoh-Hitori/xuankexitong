package com.zjsu.jh.course.controller;

import com.zjsu.jh.course.model.Enrollment;
import com.zjsu.jh.course.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /** 学生选课 */
    @PostMapping
    public ResponseEntity<?> enroll(@RequestBody Map<String, String> request) {
        String courseId = request.get("courseId");
        String studentId = request.get("studentId");
        Enrollment enrollment = enrollmentService.enroll(courseId, studentId);

        return ResponseEntity.status(201).body(Map.of(
                "code", 201,
                "message", "Created",
                "data", enrollment
        ));
    }

    /** 学生退课 */
    @DeleteMapping
    public ResponseEntity<?> drop(@RequestParam String courseId, @RequestParam String studentId) {
        enrollmentService.drop(courseId, studentId);
        return ResponseEntity.noContent().build();
    }

    /** 查询所有选课记录 */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Enrollment> enrollments = enrollmentService.getAll();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", enrollments
        ));
    }

    /** 按课程查询选课记录 */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getByCourse(@PathVariable String courseId) {
        List<Enrollment> enrollments = enrollmentService.getByCourse(courseId);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", enrollments
        ));
    }

    /** 按学生查询选课记录 */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getByStudent(@PathVariable String studentId) {
        List<Enrollment> enrollments = enrollmentService.getByStudent(studentId);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", enrollments
        ));
    }
}
