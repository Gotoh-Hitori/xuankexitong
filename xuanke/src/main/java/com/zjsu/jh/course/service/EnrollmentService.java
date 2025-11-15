package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.model.Enrollment;
import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.repository.CourseRepository;
import com.zjsu.jh.course.repository.EnrollmentRepository;
import com.zjsu.jh.course.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseRepository courseRepository,
                             StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /** 学生选课 */
    public Enrollment enroll(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));

        if (enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId).isPresent()) {
            throw new RuntimeException("该学生已选该课程");
        }

        if (course.getEnrolled() >= course.getCapacity()) {
            throw new RuntimeException("课程已满员：" + course.getTitle());
        }

        course.incrementEnrolled(); // 更新已选人数
        courseRepository.update(course);

        Enrollment enrollment = new Enrollment(UUID.randomUUID().toString(), courseId, studentId);
        return enrollmentRepository.save(enrollment);
    }

    /** 学生退课 */
    public void drop(String courseId, String studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("未找到选课记录"));

        enrollmentRepository.delete(enrollment.getId());

        courseRepository.findById(courseId).ifPresent(course -> {
            course.decrementEnrolled();
            courseRepository.update(course);
        });
    }

    /** 查询所有选课记录 */
    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }

    /** 按课程查询选课记录 */
    public List<Enrollment> getByCourse(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    /** 按学生查询选课记录 */
    public List<Enrollment> getByStudent(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
