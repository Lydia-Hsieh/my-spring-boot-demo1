package com.example.my_spring_boot_demo1.controller.loginController;

import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginVo;
import com.example.my_spring_boot_demo1.controller.loginController.service.LoginService;
import com.example.my_spring_boot_demo1.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginVo vo) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        StringBuilder errorMessage = new StringBuilder();
        //帳號或密碼錯誤
        if (!loginService.checkUserIdAndPw(vo, errorMessage)) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", errorMessage);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultMap);
        }

        //將 User ID 包在 JWT 裡回傳給前端
        String token = JwtUtil.generateToken(vo.getUserId());
        resultMap.put("status", true);
        resultMap.put("token", token);

        return ResponseEntity.ok(resultMap);
    }
}
