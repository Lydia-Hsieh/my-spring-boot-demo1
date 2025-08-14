package com.example.my_spring_boot_demo1.controller.testPropagationController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestPropagationController {

    @Autowired
    private AService service;

    @GetMapping("/ANoTransWhenBHasTransRequired")
    public ResponseEntity<Object> test1() {
        try {
            service.ANoTransWhenBHasTransRequired();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ANoTransWhenBHasTransRequiresNew")
    public ResponseEntity<Object> test2() {
        try {
            service.ANoTransWhenBHasTransRequiresNew();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/AHasTransWhenBHasTransRequired")
    public ResponseEntity<Object> test3() {
        try {
            service.AHasTransWhenBHasTransRequired();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/AHasTransWhenBHasTransRequiresNew")
    public ResponseEntity<Object> test4() {
        try {
            service.AHasTransWhenBHasTransRequiresNew();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/AHasTransWithTryCatchWhenBHasTransRequiresNew")
    public ResponseEntity<Object> test5() {
        try {
            service.AHasTransWithTryCatchWhenBHasTransRequiresNew();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
