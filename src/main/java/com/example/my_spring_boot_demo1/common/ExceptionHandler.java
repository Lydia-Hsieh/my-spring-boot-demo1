package com.example.my_spring_boot_demo1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> commonExceptionHandler(Exception e) {
        Map<String, Object> resultMap = new HashMap<>();
        log.error("An exception {} occurred: {}", e.getClass(), e.getMessage());
        resultMap.put("status", false);
        resultMap.put("errorMessage", "系統發生錯誤！");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }
}
