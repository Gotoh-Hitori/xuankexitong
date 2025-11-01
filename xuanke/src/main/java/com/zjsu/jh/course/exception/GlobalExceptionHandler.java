package com.zjsu.jh.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 409); // 使用409 Conflict状态码表示业务冲突
        res.put("message", ex.getMessage());
        res.put("data", null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    // 处理普通业务异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 400);
        res.put("message", ex.getMessage());
        res.put("data", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // 处理资源未找到异常
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 404);
        res.put("message", ex.getMessage());
        res.put("data", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    // 处理参数验证错误
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 400);
        res.put("message", message);
        res.put("data", null);
        return ResponseEntity.badRequest().body(res);
    }

    // 处理其他所有异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOther(Exception ex) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 500);
        res.put("message", "Internal Server Error: " + ex.getMessage());
        res.put("data", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}