-- 课程表
CREATE TABLE IF NOT EXISTS courses (
    id VARCHAR(36) PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(100) NOT NULL,
    instructor_id VARCHAR(36),
    instructor_name VARCHAR(50),
    instructor_email VARCHAR(100),
    schedule_day_of_week VARCHAR(20),
    schedule_start_time VARCHAR(10),
    schedule_end_time VARCHAR(10),
    schedule_expected_attendance INT,
    capacity INT NOT NULL,
    enrolled INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL
);

-- 学生表
CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(36) PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    major VARCHAR(100) NOT NULL,
    grade INT NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL
);

-- 选课表
CREATE TABLE IF NOT EXISTS enrollments (
    id VARCHAR(36) PRIMARY KEY,
    course_id VARCHAR(36) NOT NULL,
    student_id VARCHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    UNIQUE (course_id, student_id)
);

-- 索引
CREATE INDEX IF NOT EXISTS idx_enrollments_course_id ON enrollments (course_id);
CREATE INDEX IF NOT EXISTS idx_enrollments_student_id ON enrollments (student_id);