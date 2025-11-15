package com.zjsu.jh.course.model;

public class Enrollment {
    private String id;         // 系统生成 UUID
    private String courseId;   // 课程ID
    private String studentId;  // 学生ID

    public Enrollment() {}

    public Enrollment(String id, String courseId, String studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
}
