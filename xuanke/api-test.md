# 测试文档

本文件记录了课程选课系统的完整 RESTful API 测试场景。  
支持 IntelliJ IDEA `.yaml` 使用 Postman 导入执行。

---

## 基础信息

- Base URL: `http://localhost:8080`
- Content-Type: `application/json`
- 所有响应格式统一如下：
```json
{
  "code": 200,
  "message": "Success",
  "data": { }
}
课程模块 (Course)
获取所有课程
http
复制代码
GET http://localhost:8080/api/courses
Accept: application/json
添加课程
http
复制代码
POST http://localhost:8080/api/courses
Content-Type: application/json

{
  "code": "CS101",
  "title": "Java 程序设计",
  "capacity": 60
}
查询单个课程
http
复制代码
GET http://localhost:8080/api/courses/{{courseId}}
Accept: application/json
更新课程
http
复制代码
PUT http://localhost:8080/api/courses/{{courseId}}
Content-Type: application/json

{
  "code": "CS101",
  "title": "Java 程序设计（进阶）",
  "capacity": 80
}
删除课程
http
复制代码
DELETE http://localhost:8080/api/courses/{{courseId}}
学生模块 (Student)
获取所有学生
http
复制代码
GET http://localhost:8080/api/students
Accept: application/json
添加学生
http
复制代码
POST http://localhost:8080/api/students
Content-Type: application/json

{
  "studentId": "S2025001",
  "name": "张三",
  "major": "计算机科学"
}
查询单个学生
http
复制代码
GET http://localhost:8080/api/students/{{studentId}}
Accept: application/json
更新学生信息
http
复制代码
PUT http://localhost:8080/api/students/{{studentId}}
Content-Type: application/json

{
  "studentId": "S2025001",
  "name": "张三丰",
  "major": "软件工程"
}
删除学生
http
复制代码
DELETE http://localhost:8080/api/students/{{studentId}}
🧾 选课模块 (Enrollment)
获取所有选课记录
http
复制代码
GET http://localhost:8080/api/enrollments
Accept: application/json
学生选课
复制代码
POST http://localhost:8080/api/enrollments
Content-Type: application/json

{
  "courseId": "{{courseId}}",
  "studentId": "{{studentId}}"
}
学生退课
http
复制代码
DELETE http://localhost:8080/api/enrollments/{{courseId}}/{{studentId}}
按课程查询选课记录
http
复制代码
GET http://localhost:8080/api/enrollments/course/{{courseId}}
Accept: application/json
按学生查询选课记录
http
复制代码
GET http://localhost:8080/api/enrollments/student/{{studentId}}
Accept: application/json
查询单个选课记录（如果支持）
http
复制代码
GET http://localhost:8080/api/enrollments/{{enrollmentId}}
Accept: application/json
```

示例变量说明
变量名	示例值	说明
{{courseId}}	123e4567-e89b-12d3-a456-426614174000	课程 ID
{{studentId}}	S2025001	学生学号
{{enrollmentId}}	456e7890-e89b-12d3-a456-426614174001	选课记录 ID
