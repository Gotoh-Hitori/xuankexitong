package com.zjsu.jh.course.controller;

import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 查询所有学生
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", students
        ));
    }

    // 根据ID查询学生
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", student
        ));
    }

    // 创建学生
    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student) {
        Student created = studentService.createStudent(student);
        return ResponseEntity.status(201).body(Map.of(
                "code", 201,
                "message", "Created",
                "data", created
        ));
    }

    // 更新学生信息
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @Valid @RequestBody Student student) {
        Student updated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "message", "Success",
                "data", updated
        ));
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
