package com.zjsu.jh.course.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"course_id", "student_id"})
}, indexes = {
    @Index(name = "idx_enrollments_course_id", columnList = "course_id"),
    @Index(name = "idx_enrollments_student_id", columnList = "student_id")
})
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;         // 系统生成 UUID

    @Column(name = "course_id", nullable = false)
    private String courseId;   // 课程ID

    @Column(name = "student_id", nullable = false)
    private String studentId;  // 学生ID

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnrollmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Enrollment() {}

    public Enrollment(String id, String courseId, String studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
        this.status = EnrollmentStatus.ACTIVE;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}