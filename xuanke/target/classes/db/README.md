# 数据库初始化说明

## 目录结构

- [schema.sql](file://C:\Users\Bravo\Downloads\xuankexitong-main\xuankexitong-main\xuanke\src\main\resources\db\schema.sql)：数据库表结构定义
- [data.sql](file://C:\Users\Bravo\Downloads\xuankexitong-main\xuankexitong-main\xuanke\src\main\resources\db\data.sql)：基础测试数据

## 自动初始化（开发环境）

在开发环境中，系统使用H2内存数据库，并配置为自动执行初始化脚本：

1. 应用启动时自动创建表结构
2. 自动插入基础测试数据

此功能通过以下配置启用：
```yaml
spring:
  sql:
    init:
      mode: always
      schema-locations: classpath:db/schema.sql
      data-locations: classpath:db/data.sql
```

## 手动初始化（生产环境）

在生产环境中，需要手动执行数据库初始化：

### 1. 创建数据库

```sql
CREATE DATABASE xuanke_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 执行建表脚本

```bash
mysql -u root -p xuanke_db < src/main/resources/db/schema.sql
```

### 3. 插入初始数据（可选）

```bash
mysql -u root -p xuanke_db < src/main/resources/db/data.sql
```

## 表结构说明

### courses（课程表）
- id: 主键
- code: 课程代码（唯一）
- title: 课程标题
- instructor_id: 教师ID
- instructor_name: 教师姓名
- instructor_email: 教师邮箱
- schedule_day_of_week: 上课日
- schedule_start_time: 开始时间
- schedule_end_time: 结束时间
- schedule_expected_attendance: 预期人数
- capacity: 课程容量
- enrolled: 已选人数
- created_at: 创建时间

### students（学生表）
- id: 主键
- student_id: 学号（唯一）
- name: 姓名
- major: 专业
- grade: 年级
- email: 邮箱（唯一）
- created_at: 创建时间

### enrollments（选课表）
- id: 主键
- course_id: 课程ID
- student_id: 学生ID
- status: 选课状态（ACTIVE, DROPPED, COMPLETED）
- created_at: 创建时间

## 注意事项

1. 在生产环境中，`spring.sql.init.mode` 设置为 `never`，防止应用启动时自动执行SQL脚本
2. 建议在生产环境中使用专业的数据库迁移工具（如Flyway或Liquibase）来管理数据库变更
3. 初始化脚本中的示例数据仅用于开发和测试环境