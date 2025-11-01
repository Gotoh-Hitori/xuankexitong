-- 插入示例课程数据
INSERT INTO courses (id, code, title, instructor_id, instructor_name, instructor_email, 
                     schedule_day_of_week, schedule_start_time, schedule_end_time, 
                     schedule_expected_attendance, capacity, enrolled, created_at) 
VALUES 
('1', 'CS101', '计算机科学导论', 'T001', '张教授', 'zhang@example.edu.cn', 
 'MONDAY', '08:00', '10:00', 50, 60, 0, NOW()),
('2', 'CS102', '数据结构', 'T002', '李教授', 'li@example.edu.cn', 
 'TUESDAY', '10:00', '12:00', 50, 50, 0, NOW());

-- 插入示例学生数据
INSERT INTO students (id, student_id, name, major, grade, email, created_at) 
VALUES 
('1', 'S2024001', '张三', '计算机科学与技术', 2024, 'zhangsan@example.com', NOW()),
('2', 'S2024002', '李四', '软件工程', 2024, 'lisi@example.com', NOW());