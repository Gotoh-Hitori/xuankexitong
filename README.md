# 选课管理系统

本项目是一个基于 **Spring Boot 3 + Java 17** 实现的选课系统示例，  
展示了课程、学生、选课的完整 RESTful API 设设计与实现。  
系统支持多种数据库环境（H2内存数据库用于开发，MySQL用于生产环境）。

---

## 一、项目结构

```
xuanke/
 ├── src/
 │   ├── main/java/com/zjsu/jh/course/
 │   │   ├── controller/      # 控制层（EnrollmentController、CourseController、StudentController等）
 │   │   ├── service/         # 服务层（EnrollmentService、CourseService、StudentService）
 │   │   ├── model/           # 实体类（Course、Student、Enrollment等）
 │   │   ├── repository/      # 数据访问层（JPA Repository接口）
 │   │   ├── exception/       # 自定义异常类和全局异常处理
 │   └── resources/
 │       ├── db/              # 数据库初始化脚本
 │       ├── application.yml  # Spring Boot 主配置文件
 │       ├── application-dev.yml  # 开发环境配置
 │       └── application-prod.yml # 生产环境配置
 ├── pom.xml                  # Maven 依赖配置
 └── README.md                # 项目说明
```

---

## 二、运行说明

### 环境要求

- JDK 17+  
- Maven 3.8+  
- IntelliJ IDEA 或其他 IDE
- （可选）MySQL 8.0+（用于生产环境）

### 环境切换

系统支持两种运行环境：

1. **开发环境**（默认）：使用H2内存数据库
2. **生产环境**：使用MySQL数据库

切换方式：

```bash
# 使用开发环境（默认）
mvn spring-boot:run

# 使用生产环境
mvn spring-boot:run -Dspring.profiles.active=prod
```

### 构建与运行

```bash
# 编译项目
mvn clean package

# 运行项目（开发环境）
mvn spring-boot:run

# 运行项目（生产环境）
mvn spring-boot:run -Dspring.profiles.active=prod
```

### 访问地址

服务启动后可访问：  
📍 http://localhost:8080/

H2控制台（仅开发环境）：  
📍 http://localhost:8080/h2-console

---

## 三、数据库配置

### 开发环境（H2数据库）

- 自动创建内存数据库
- 自动执行初始化脚本（schema.sql和data.sql）
- 启用H2控制台，便于调试
- 启用SQL日志显示

### 生产环境（MySQL数据库）

- 需要手动创建数据库
- 使用HikariCP连接池
- 关闭SQL日志显示
- 需要手动执行初始化脚本

### 数据库健康检查

系统提供数据库连接健康检查接口：

```bash
curl http://localhost:8080/health/db
```

---

## 📚 四、API 接口详细说明

### 课程管理模块

#### 1. 获取所有课程

- **URL**: `GET /api/courses`

- **功能**: 获取系统中所有课程信息

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/courses
  ```

- **响应示例**:

  ```json
  {
    "code": 200,
    "message": "Success",
    "data": [
      {
        "id": "1",
        "code": "CS101",
        "title": "计算机科学导论",
        "instructor": {
          "id": "T001",
          "name": "张教授",
          "email": "zhang@example.edu.cn"
        },
        "schedule": {
          "dayOfWeek": "MONDAY",
          "startTime": "08:00",
          "endTime": "10:00",
          "expectedAttendance": 50
        },
        "capacity": 60,
        "enrolled": 0
      }
    ]
  }
  ```

#### 2. 获取单个课程

- **URL**: `GET /api/courses/{id}`

- **功能**: 根据课程ID获取特定课程信息

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/courses/1
  ```

#### 3. 创建课程

- **URL**: `POST /api/courses`

- **功能**: 添加新课程到系统中

- **请求示例**:

  ```bash
  curl -X POST http://localhost:8080/api/courses \
    -H "Content-Type: application/json" \
    -d '{
      "code": "CS102",
      "title": "数据结构",
      "instructor": {
        "id": "T002",
        "name": "李教授",
        "email": "li@example.edu.cn"
      },
      "schedule": {
        "dayOfWeek": "TUESDAY",
        "startTime": "10:00",
        "endTime": "12:00",
        "expectedAttendance": 50
      },
      "capacity": 50
    }'
  ```

#### 4. 更新课程

- **URL**: `PUT /api/courses/{id}`

- **功能**: 更新指定课程的信息

- **请求示例**:

  ```bash
  curl -X PUT http://localhost:8080/api/courses/1 \
    -H "Content-Type: application/json" \
    -d '{
      "code": "CS101",
      "title": "计算机科学导论（进阶）",
      "instructor": {
        "id": "T001",
        "name": "张教授",
        "email": "zhang@example.edu.cn"
      },
      "schedule": {
        "dayOfWeek": "MONDAY",
        "startTime": "08:00",
        "endTime": "10:00",
        "expectedAttendance": 60
      },
      "capacity": 70
    }'
  ```

#### 5. 删除课程

- **URL**: `DELETE /api/courses/{id}`

- **功能**: 根据课程ID删除课程

- **请求示例**:

  ```bash
  curl -X DELETE http://localhost:8080/api/courses/1
  ```

### 学生管理模块

#### 1. 获取所有学生

- **URL**: `GET /api/students`

- **功能**: 获取系统中所有学生信息

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/students
  ```

#### 2. 获取单个学生

- **URL**: `GET /api/students/{id}`

- **功能**: 根据学生ID获取特定学生信息

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/students/1
  ```

#### 3. 创建学生

- **URL**: `POST /api/students`

- **功能**: 添加新学生到系统中

- **请求示例**:

  ```bash
  curl -X POST http://localhost:8080/api/students \
    -H "Content-Type: application/json" \
    -d '{
      "studentId": "S2024001",
      "name": "张三",
      "major": "计算机科学与技术",
      "grade": 2024,
      "email": "zhangsan@example.com"
    }'
  ```

#### 4. 更新学生

- **URL**: `PUT /api/students/{id}`

- **功能**: 更新指定学生的信息

- **请求示例**:

  ```bash
  curl -X PUT http://localhost:8080/api/students/1 \
    -H "Content-Type: application/json" \
    -d '{
      "studentId": "S2024001",
      "name": "张三丰",
      "major": "软件工程",
      "grade": 2024,
      "email": "zhangsan@example.com"
    }'
  ```

#### 5. 删除学生

- **URL**: `DELETE /api/students/{id}`

- **功能**: 根据学生ID删除学生

- **请求示例**:

  ```bash
  curl -X DELETE http://localhost:8080/api/students/1
  ```

### 选课管理模块

#### 1. 学生选课

- **URL**: `POST /api/enrollments`

- **功能**: 学生选择课程

- **请求示例**:

  ```bash
  curl -X POST http://localhost:8080/api/enrollments \
    -H "Content-Type: application/json" \
    -d '{
      "courseId": "1",
      "studentId": "1"
    }'
  ```

#### 2. 学生退课

- **URL**: `DELETE /api/enrollments?courseId={courseId}&studentId={studentId}`

- **功能**: 学生退出已选课程

- **请求示例**:

  ```bash
  curl -X DELETE "http://localhost:8080/api/enrollments?courseId=1&studentId=1"
  ```

#### 3. 获取所有选课记录

- **URL**: `GET /api/enrollments`

- **功能**: 获取系统中所有选课记录

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/enrollments
  ```

#### 4. 按课程查询选课记录

- **URL**: `GET /api/enrollments/course/{courseId}`

- **功能**: 查询某门课程的所有选课记录

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/enrollments/course/1
  ```

#### 5. 按学生查询选课记录

- **URL**: `GET /api/enrollments/student/{studentId}`

- **功能**: 查询某个学生的所有选课记录

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/api/enrollments/student/1
  ```

### 健康检查模块

#### 数据库连接检查

- **URL**: `GET /health/db`

- **功能**: 检查数据库连接状态

- **请求示例**:

  ```bash
  curl -X GET http://localhost:8080/health/db
  ```

---

## 五、测试说明

使用 **Postman** 进行接口调试。

示例测试文件见：

- [api-test.md](api-test.md) - 包含详细的API测试用例
- [test-api.yaml](src/main/resources/test-api.yaml) - OpenAPI 3.0规范定义文件，可直接导入Postman

### 响应格式说明

系统所有API响应都遵循统一的JSON格式：

```json
{
  "code": 200,           // 状态码
  "message": "Success",  // 响应消息
  "data": { }            // 实际数据
}
```

### 错误处理

系统会根据不同的错误情况返回相应的HTTP状态码和错误信息：

- `400 Bad Request`: 请求参数错误
- `404 Not Found`: 请求的资源不存在
- `409 Conflict`: 业务冲突（如重复选课、学号重复等）
- `500 Internal Server Error`: 服务器内部错误

### 示例变量说明

在测试文件中，以下变量需要替换为实际值：

| 变量名          | 示例值                               | 说明     |
| --------------- | ------------------------------------ | -------- |
| `{{courseId}}`  | 123e4567-e89b-12d3-a456-426614174000 | 课程 ID  |
| `{{studentId}}` | S2025001                             | 学生学号 |
