package com.example.my_spring_boot_demo1.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ResponseService {

    public ResponseEntity<Map<String, Object>> ok(Map<String, Object> resultMap) {
        resultMap.put("status", true);
        return ResponseEntity.ok(resultMap);
    }

    /**
     * fail
     * @param resultMap response body
     * @param statusCode HTTP status code
     * @param errorMessage error message to user
     * @return
     */
    public ResponseEntity<Map<String, Object>> fail(Map<String, Object> resultMap, int statusCode,String errorMessage) {
        resultMap.put("status", false);
        resultMap.put("errorMessage", errorMessage);
        return ResponseEntity.status(statusCode).body(resultMap);
    }
}
