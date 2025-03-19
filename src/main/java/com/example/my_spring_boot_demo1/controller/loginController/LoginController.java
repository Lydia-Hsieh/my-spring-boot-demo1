package com.example.my_spring_boot_demo1.controller.loginController;

import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginRequest;
import com.example.my_spring_boot_demo1.controller.loginController.service.LoginService;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import com.example.my_spring_boot_demo1.service.ResponseService;
import com.example.my_spring_boot_demo1.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/account-login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginRequest request) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        StringBuilder errorMessage = new StringBuilder();
        //帳號或密碼錯誤
        if (!loginService.checkUserIdAndPw(request, errorMessage)) {
            return responseService.fail(resultMap, HttpStatus.UNAUTHORIZED.value(), errorMessage.toString());
        }

        //將 User ID 包在 JWT 裡回傳給前端
        String token = JwtUtil.generateToken(request.getUserId());
        resultMap.put("token", token);
        return responseService.ok(resultMap);
    }
}
