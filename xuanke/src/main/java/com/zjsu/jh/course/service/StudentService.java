package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        if (studentRepository.findByStudentId(student.getStudentId()).isPresent()) {
            throw new RuntimeException("学号已存在：" + student.getStudentId());
        }
        student.setCreatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在 ID=" + id));
    }

    public Student updateStudent(String id, Student updated) {
        Student existing = getStudentById(id);
        existing.setName(updated.getName());
        existing.setMajor(updated.getMajor());
        existing.setGrade(updated.getGrade());
        existing.setEmail(updated.getEmail());
        return studentRepository.save(existing);
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
