package com.example.my_spring_boot_demo1.controller.changePwController;

import com.example.my_spring_boot_demo1.util.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePwController {

    @PostMapping("/changePw")
    public String changePw() {
        return "";
    }
}
