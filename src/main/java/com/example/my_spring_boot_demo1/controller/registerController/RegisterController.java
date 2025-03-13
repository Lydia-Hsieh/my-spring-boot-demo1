package com.example.my_spring_boot_demo1.controller.registerController;

import com.example.my_spring_boot_demo1.controller.registerController.pojo.AccountVo;
import com.example.my_spring_boot_demo1.controller.registerController.service.RegisterService;
import com.example.my_spring_boot_demo1.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AccountVo vo) {
        Map<String, Object> resultMap = new HashMap<>();

        //參數檢查
        String userId = vo.getUserId();
        String password = vo.getPassword();
        if (userId == null || !StringUtil.checkUserId(userId)) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", "帳號僅能使用英文字母、數字及底線！");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }
        if (registerService.checkUserIdIsUsed(userId)) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", "該帳號已存在，請使用其他帳號名註冊！");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }
        if (password == null || !StringUtil.checkPassword(password)) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", "密碼需包含英文字母大小寫、數字以及特殊字元，長度至少有8位數！");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }

        try {
            registerService.register(vo);
            resultMap.put("status", true);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", "註冊失敗");
            log.error("An exception {} occurred when registering: {}", e.getClass(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }
    }
}
