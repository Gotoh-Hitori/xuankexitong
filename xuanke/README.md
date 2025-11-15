# 选课管理系统

本项目是一个基于 **Spring Boot 3 + Java 17** 实现的选课系统示例，  
展示了课程、学生、选课的完整 RESTful API 设计与实现。  
系统使用内存数据结构（`ConcurrentHashMap`）存储数据，无需数据库依赖，  
可直接运行与测试。

---

## 一、项目结构

```
xuanke/
 ├── src/
 │   ├── main/java/com/zjsu/jh/course/
 │   │   ├── controller/      # 控制层（EnrollmentController、CourseController、StudentController）
 │   │   ├── service/         # 服务层（EnrollmentService、CourseService、StudentService）
 │   │   ├── model/           # 实体类（Course、Student、Enrollment等）
 │   │   ├── repository/      # 内存仓库
 │   │   ├── exception/       # 自定义异常类和全局异常处理
 │   └── resources/
 │       └── application.yml  # Spring Boot 配置文件
 ├── pom.xml                  # Maven 依赖配置
 └── README.md                # 项目说明
```

---

## 二、运行说明

### 环境要求
- JDK 17+  
- Maven 3.8+  
- IntelliJ IDEA 或其他 IDE

### 构建与运行
```bash
# 编译项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

### 访问地址
服务启动后可访问：  
📍 http://localhost:8080/

---

## 📚 三、API 接口列表

| 模块 | 功能 | 方法 | URL |
|------|------|------|------|
| 课程 | 获取所有课程 | GET | `/api/courses` |
| 课程 | 添加课程 | POST | `/api/courses` |
| 课程 | 删除课程 | DELETE | `/api/courses/{id}` |
| 学生 | 获取所有学生 | GET | `/api/students` |
| 学生 | 添加学生 | POST | `/api/students` |
| 选课 | 学生选课 | POST | `/api/enrollments` |
| 选课 | 学生退课 | DELETE | `/api/enrollments/{courseId}/{studentId}` |
| 选课 | 按学生查询选课 | GET | `/api/enrollments/student/{studentId}` |
| 选课 | 按课程查询选课 | GET | `/api/enrollments/course/{courseId}` |

---

## 四、测试说明

使用  **Postman** 进行接口调试。  

示例测试文件见：  
- api-test.md
- openapi.yaml

