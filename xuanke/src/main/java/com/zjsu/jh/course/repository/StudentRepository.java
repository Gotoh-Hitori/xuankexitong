package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Student;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StudentRepository {
    private final Map<String, Student> studentMap = new ConcurrentHashMap<>();

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(UUID.randomUUID().toString());
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    public Optional<Student> findById(String id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    public Optional<Student> findByStudentId(String studentId) {
        return studentMap.values().stream()
                .filter(s -> s.getStudentId().equalsIgnoreCase(studentId))
                .findFirst();
    }

    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    public void deleteById(String id) {
        studentMap.remove(id);
    }

    public void clear() {
        studentMap.clear();
    }
}
