package com.example.my_spring_boot_demo1.common;

import com.example.my_spring_boot_demo1.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @Autowired
    private ResponseService responseService;

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> commonExceptionHandler(Exception e) {
        log.error("An exception {} occurred: {}", e.getClass(), e.getMessage());
        return responseService.fail(
                new HashMap<>(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "系統發生錯誤！");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validExceptionHandler(MethodArgumentNotValidException e) {
        log.error("An exception {} occurred: {}", e.getClass(), e.getMessage());
        return responseService.fail(
                new HashMap<>(),
                HttpStatus.BAD_REQUEST.value(),
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
