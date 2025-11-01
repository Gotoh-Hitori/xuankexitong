package com.zjsu.jh.course.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "code")
})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // UUID

    @NotBlank(message = "课程代码不能为空")
    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank(message = "课程标题不能为空")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "任课教师不能为空")
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "instructor_id")),
        @AttributeOverride(name = "name", column = @Column(name = "instructor_name")),
        @AttributeOverride(name = "email", column = @Column(name = "instructor_email"))
    })
    private Instructor instructor;

    @NotNull(message = "课程时间不能为空")
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dayOfWeek", column = @Column(name = "schedule_day_of_week")),
        @AttributeOverride(name = "startTime", column = @Column(name = "schedule_start_time")),
        @AttributeOverride(name = "endTime", column = @Column(name = "schedule_end_time")),
        @AttributeOverride(name = "expectedAttendance", column = @Column(name = "schedule_expected_attendance"))
    })
    private ScheduleSlot schedule;

    @NotNull(message = "课程容量不能为空")
    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer enrolled = 0; // 当前已选人数，初始为0

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Course() {}

    public Course(String id, String code, String title, Instructor instructor, ScheduleSlot schedule, Integer capacity) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.schedule = schedule;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    // ===== Getters & Setters =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public ScheduleSlot getSchedule() { return schedule; }
    public void setSchedule(ScheduleSlot schedule) { this.schedule = schedule; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getEnrolled() { return enrolled; }
    public void setEnrolled(Integer enrolled) { this.enrolled = enrolled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ===== 已选人数操作 =====
    /** 已选人数加一 */
    public void incrementEnrolled() {
        if (enrolled == null) enrolled = 0;
        enrolled++;
    }

    /** 已选人数减一，最小为0 */
    public void decrementEnrolled() {
        if (enrolled == null) enrolled = 0;
        enrolled = Math.max(0, enrolled - 1);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", instructor=" + instructor +
                ", schedule=" + schedule +
                ", capacity=" + capacity +
                ", enrolled=" + enrolled +
                ", createdAt=" + createdAt +
                '}';
    }
}