package com.example.my_spring_boot_demo1.controller.changePwController;

import com.example.my_spring_boot_demo1.controller.changePwController.pojo.ChangePwRequest;
import com.example.my_spring_boot_demo1.controller.changePwController.service.ChangePwService;
import com.example.my_spring_boot_demo1.service.ResponseService;
import com.example.my_spring_boot_demo1.util.StringUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangePwController {

    @Autowired
    private ChangePwService changePwService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/change-password")
    public ResponseEntity<Map<String, Object>> changePw(@RequestBody @Valid ChangePwRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            changePwService.changePw(request);
            return responseService.ok(resultMap);
        } catch (Exception e) {
            return responseService.fail(resultMap, HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }
}
