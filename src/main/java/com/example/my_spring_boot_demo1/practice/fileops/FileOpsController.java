package com.example.my_spring_boot_demo1.practice.fileops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-ops")
public class FileOpsController {

    @Autowired
    private FileOpsService fileOpsService;

    @GetMapping
    public ResponseEntity copyFile() {
        fileOpsService.copyFileFromSourceToTarget();
        return ResponseEntity.ok().build();
    }
}
