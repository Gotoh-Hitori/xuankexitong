# 选课管理系统

基于 **Spring Boot 3.4 + Java 17** 构建的轻量级课程-学生-选课管理 API。系统以 `ConcurrentHashMap` 作为内存数据存储，开箱即用；提供课程容量控制、选课幂等校验、统一异常处理以及 Docker 化部署示例，适合作为课堂/面试示例或二次开发模板。

> 项目启动后会在 `@PostConstruct` 中预置两门课程与两名学生，可直接调用接口体验。

---

## 1. 技术栈与特性

- Spring Boot Web & Validation：暴露 RESTful API，并提供 `@Valid` 参数校验。
- 分层设计：Controller / Service / Repository / Model / Exception。
- 并发安全：仓储层使用 `ConcurrentHashMap`，并通过服务层封装业务逻辑。
- 统一响应：所有接口返回 `code`、`message`、`data` 结构，便于前端消费。
- 全局异常处理：`GlobalExceptionHandler` 对业务、校验、系统异常做集中拦截。
- 容器化：提供 `Dockerfile` 与 `docker-compose.yml`，可一键构建运行环境。

---

## 2. 项目结构

```
xuanke/
├── src/main/java/com/zjsu/jh/course
│   ├── controller/        # CourseController / StudentController / EnrollmentController
│   ├── service/           # 业务逻辑、容量校验、幂等校验
│   ├── repository/        # 基于 ConcurrentHashMap 的内存仓储
│   ├── model/             # Course / Student / Enrollment / Instructor / ScheduleSlot
│   └── exception/         # ResourceNotFoundException + GlobalExceptionHandler
├── src/main/resources/
│   ├── application.properties  # 默认 profile
│   ├── application-*.yml       # 预留 dev / prod / docker 配置
│   └── test-api.yaml           # OpenAPI 3.0 描述文件
├── api-test.md                 # Postman / HTTPie 调试脚本
├── docker-compose.yml          # App + MySQL 示例编排
├── Dockerfile                  # 多阶段构建镜像
└── pom.xml
```

---

## 3. 核心数据模型

| 模型           | 关键字段                                                     | 说明                                                         |
| -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `Course`       | `code`、`title`、`Instructor`、`ScheduleSlot`、`capacity`、`enrolled` | 支持容量控制、自动生成 `id`，在选课/退课时维护 `enrolled` 计数。 |
| `Student`      | `studentId`、`name`、`major`、`grade`、`email`               | 启用 `@Email`、`@NotBlank` 校验，并记录 `createdAt`。        |
| `Enrollment`   | `courseId`、`studentId`                                      | 使用 UUID 作为主键，防止重复选课。                           |
| `Instructor`   | `id`、`name`、`email`                                        | 任课教师信息，挂载在课程上。                                 |
| `ScheduleSlot` | `dayOfWeek`、`startTime`、`endTime`、`expectedAttendance`    | 描述排课时段与预期到课人数。                                 |

---

## 4. 运行方式

### 4.1 环境要求

- JDK 17+
- Maven 3.8+
- （可选）Docker 24+、Docker Compose

### 4.2 本地运行

```bash
# 安装依赖并启动（热加载）
mvn spring-boot:run

# 或先构建再运行
mvn clean package
java -jar target/course-1.0.0.jar
```

- 默认端口：`http://localhost:8080`
- 默认 profile 为 `application.properties`，无需数据库即可运行。

### 4.3 Docker 镜像

```bash
# 构建
docker build -t coursehub:latest .

# 运行
docker run --rm -p 8080:8080 coursehub:latest
```

### 4.4 Docker Compose

`docker-compose.yml` 提供 App + MySQL 的示例编排（预设 `SPRING_PROFILES_ACTIVE=docker`）。目前应用仍以内存仓储为主，因此 MySQL 配置可视为后续扩展示例。

```bash
docker compose up -d --build
```

---

## 5. API 速查

| 模块 | 操作       | 方法   | URL                                                 |
| ---- | ---------- | ------ | --------------------------------------------------- |
| 课程 | 查询全部   | GET    | `/api/courses`                                      |
| 课程 | 查询单个   | GET    | `/api/courses/{id}`                                 |
| 课程 | 新增       | POST   | `/api/courses`                                      |
| 课程 | 更新       | PUT    | `/api/courses/{id}`                                 |
| 课程 | 删除       | DELETE | `/api/courses/{id}`                                 |
| 学生 | 查询全部   | GET    | `/api/students`                                     |
| 学生 | 查询单个   | GET    | `/api/students/{id}`                                |
| 学生 | 新增       | POST   | `/api/students`                                     |
| 学生 | 更新       | PUT    | `/api/students/{id}`                                |
| 学生 | 删除       | DELETE | `/api/students/{id}`                                |
| 选课 | 查询全部   | GET    | `/api/enrollments`                                  |
| 选课 | 按课程查询 | GET    | `/api/enrollments/course/{courseId}`                |
| 选课 | 按学生查询 | GET    | `/api/enrollments/student/{studentId}`              |
| 选课 | 选课       | POST   | `/api/enrollments`（Body：`courseId`、`studentId`） |
| 选课 | 退课       | DELETE | `/api/enrollments?courseId=...&studentId=...`       |

---

## 6. 示例请求

创建课程：

```http
POST /api/courses
Content-Type: application/json

{
  "code": "CS301",
  "title": "分布式系统",
  "instructor": {
    "id": "T100",
    "name": "王老师",
    "email": "wang@example.edu.cn"
  },
  "schedule": {
    "dayOfWeek": "THURSDAY",
    "startTime": "14:00",
    "endTime": "16:00",
    "expectedAttendance": 60
  },
  "capacity": 80
}
```

学生选课：

```http
POST /api/enrollments
Content-Type: application/json

{
  "courseId": "课程ID",
  "studentId": "学生ID"
}
```

- 若课程已满或重复选课，`EnrollmentService` 将抛出业务异常，由全局异常处理器返回 `400` 错误。

---

## 7. 错误处理约定

- **业务异常**（如课程已满、学号重复）：`code=400`、`HttpStatus.BAD_REQUEST`。
- **资源不存在**：`code=404`、`ResourceNotFoundException`。
- **参数校验失败**：`MethodArgumentNotValidException`，自动返回第一条校验信息。
- **未知错误**：`code=500`，携带 `Internal Server Error` 提示。

---

## 8. 调试与测试

- `api-test.md`：包含 `curl`/`HTTPie` 示例，可快速验证核心流程。
- `src/main/resources/test-api.yaml`：OpenAPI 3.0 描述文件，可导入 Swagger UI、Apifox、Postman。

