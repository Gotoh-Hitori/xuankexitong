package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.BusinessException;
import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Course;
import com.zjsu.jh.course.model.Enrollment;
import com.zjsu.jh.course.model.EnrollmentStatus;
import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.repository.CourseRepository;
import com.zjsu.jh.course.repository.EnrollmentRepository;
import com.zjsu.jh.course.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
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
    @Transactional
    public Enrollment enroll(String courseId, String studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("课程不存在"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在"));

        if (enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE)) {
            throw new BusinessException("该学生已选该课程");
        }

        if (course.getEnrolled() >= course.getCapacity()) {
            throw new BusinessException("课程已满员：" + course.getTitle());
        }

        course.incrementEnrolled(); // 更新已选人数
        courseRepository.save(course); // 使用JPA的save方法

        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(courseId);
        enrollment.setStudentId(studentId);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setCreatedAt(LocalDateTime.now());
        return enrollmentRepository.save(enrollment);
    }

    /** 学生退课 */
    @Transactional
    public void drop(String courseId, String studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentIdAndStatus(courseId, studentId, EnrollmentStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("未找到选课记录"));

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);

        courseRepository.findById(courseId).ifPresent(course -> {
            course.decrementEnrolled();
            courseRepository.save(course); // 使用JPA的save方法
        });
    }

    /** 查询所有选课记录 */
    @Transactional(readOnly = true)
    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }

    /** 按课程查询选课记录 */
    @Transactional(readOnly = true)
    public List<Enrollment> getByCourse(String courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    /** 按学生查询选课记录 */
    @Transactional(readOnly = true)
    public List<Enrollment> getByStudent(String studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
    
    // 新增方法：按课程和状态查询
    @Transactional(readOnly = true)
    public List<Enrollment> getByCourseAndStatus(String courseId, EnrollmentStatus status) {
        return enrollmentRepository.findByCourseIdAndStatus(courseId, status);
    }
    
    // 新增方法：按学生和状态查询
    @Transactional(readOnly = true)
    public List<Enrollment> getByStudentAndStatus(String studentId, EnrollmentStatus status) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, status);
    }
    
    // 新增方法：统计课程活跃人数
    @Transactional(readOnly = true)
    public long getActiveEnrollmentCount(String courseId) {
        return enrollmentRepository.countActiveEnrollmentsByCourseId(courseId);
    }
    
    // 新增方法：判断学生是否已选课
    @Transactional(readOnly = true)
    public boolean isStudentEnrolled(String studentId, String courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE);
    }
}