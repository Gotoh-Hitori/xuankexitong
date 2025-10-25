package com.zjsu.jh.course;

import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.model.Instructor;
import com.zjsu.jh.course.model.ScheduleSlot;
import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.service.CourseService;
import com.zjsu.jh.course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;

@SpringBootApplication
public class XuankeApplication {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(XuankeApplication.class, args);
    }

    @PostConstruct
    public void initData() {
        // 初始化示例课程
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("计算机科学导论");
        course1.setInstructor(new Instructor("T001", "张教授", "zhang@example.edu.cn"));
        course1.setSchedule(new ScheduleSlot("MONDAY", "08:00", "10:00", 50));
        course1.setCapacity(60);
        courseService.createCourse(course1);

        Course course2 = new Course();
        course2.setCode("CS102");
        course2.setTitle("数据结构");
        course2.setInstructor(new Instructor("T002", "李教授", "li@example.edu.cn"));
        course2.setSchedule(new ScheduleSlot("TUESDAY", "10:00", "12:00", 50));
        course2.setCapacity(50);
        courseService.createCourse(course2);

        // 初始化示例学生
        Student student1 = new Student();
        student1.setStudentId("S2024001");
        student1.setName("张三");
        student1.setMajor("计算机科学与技术");
        student1.setGrade(2024);
        student1.setEmail("zhangsan@example.com");
        student1.setCreatedAt(LocalDateTime.now());
        studentService.createStudent(student1);

        Student student2 = new Student();
        student2.setStudentId("S2024002");
        student2.setName("李四");
        student2.setMajor("软件工程");
        student2.setGrade(2024);
        student2.setEmail("lisi@example.com");
        student2.setCreatedAt(LocalDateTime.now());
        studentService.createStudent(student2);
    }
}
