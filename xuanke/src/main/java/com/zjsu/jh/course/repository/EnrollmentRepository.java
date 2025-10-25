package com.zjsu.jh.course.repository;

import com.zjsu.jh.course.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository {
    private final Map<String, Enrollment> enrollmentMap = new ConcurrentHashMap<>();

    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null) {
            enrollment.setId(UUID.randomUUID().toString());
        }
        enrollmentMap.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    // ✅ 新增 findById 方法
    public Optional<Enrollment> findById(String id) {
        return Optional.ofNullable(enrollmentMap.get(id));
    }

    // ✅ 修改 deleteById → delete，以匹配 service 调用
    public void delete(String id) {
        enrollmentMap.remove(id);
    }

    public Optional<Enrollment> findByCourseIdAndStudentId(String courseId, String studentId) {
        return enrollmentMap.values().stream()
                .filter(e -> e.getCourseId().equals(courseId) && e.getStudentId().equals(studentId))
                .findFirst();
    }

    public List<Enrollment> findByCourseId(String courseId) {
        return enrollmentMap.values().stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .collect(Collectors.toList());
    }

    public List<Enrollment> findByStudentId(String studentId) {
        return enrollmentMap.values().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollmentMap.values());
    }
}
