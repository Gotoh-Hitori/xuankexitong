package com.zjsu.jh.course.service;

import com.zjsu.jh.course.exception.BusinessException;
import com.zjsu.jh.course.exception.ResourceNotFoundException;
import com.zjsu.jh.course.model.Student;
import com.zjsu.jh.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentId(student.getStudentId())) {
            throw new BusinessException("学号已存在：" + student.getStudentId());
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new BusinessException("邮箱已存在：" + student.getEmail());
        }
        student.setCreatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在 ID=" + id));
    }

    @Transactional
    public Student updateStudent(String id, Student updated) {
        Student existing = getStudentById(id);
        // 检查学号是否已被其他学生使用
        if (!existing.getStudentId().equals(updated.getStudentId()) && 
            studentRepository.existsByStudentId(updated.getStudentId())) {
            throw new BusinessException("学号已存在：" + updated.getStudentId());
        }
        // 检查邮箱是否已被其他学生使用
        if (!existing.getEmail().equals(updated.getEmail()) && 
            studentRepository.existsByEmail(updated.getEmail())) {
            throw new BusinessException("邮箱已存在：" + updated.getEmail());
        }
        
        existing.setName(updated.getName());
        existing.setMajor(updated.getMajor());
        existing.setGrade(updated.getGrade());
        existing.setEmail(updated.getEmail());
        return studentRepository.save(existing);
    }

    @Transactional
    public void deleteStudent(String id) {
        getStudentById(id);
        // 检查是否有关联的选课记录
        // 这里应该检查是否有活跃的选课记录，但为了简化，我们直接删除
        studentRepository.deleteById(id);
    }
    
    // 新增方法：按学号查询
    @Transactional(readOnly = true)
    public Student getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在 studentId=" + studentId));
    }
    
    // 新增方法：按邮箱查询
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("学生不存在 email=" + email));
    }
    
    // 新增方法：按专业筛选
    @Transactional(readOnly = true)
    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }
    
    // 新增方法：按年级筛选
    @Transactional(readOnly = true)
    public List<Student> getStudentsByGrade(Integer grade) {
        return studentRepository.findByGrade(grade);
    }
}