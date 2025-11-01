package com.zjsu.jh.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> checkDatabaseConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try (Connection connection = dataSource.getConnection()) {
            boolean isValid = connection.isValid(2); // 2秒超时
            response.put("status", isValid ? "UP" : "DOWN");
            response.put("database", connection.getMetaData().getDatabaseProductName());
            response.put("driver", connection.getMetaData().getDriverName());
            response.put("message", isValid ? "Database connection successful" : "Database connection failed");
            
            return ResponseEntity.ok(response);
        } catch (SQLException e) {
            response.put("status", "DOWN");
            response.put("message", "Database connection failed: " + e.getMessage());
            return ResponseEntity.status(503).body(response);
        }
    }
}