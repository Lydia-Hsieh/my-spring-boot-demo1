package com.example.my_spring_boot_demo1.controller.registerController;

import com.example.my_spring_boot_demo1.controller.registerController.pojo.RegisterRequest;
import com.example.my_spring_boot_demo1.controller.registerController.service.RegisterService;
import com.example.my_spring_boot_demo1.service.MessageService;
import com.example.my_spring_boot_demo1.service.ResponseService;
import com.example.my_spring_boot_demo1.util.StringUtil;
import jakarta.validation.Valid;
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

    @Autowired
    private ResponseService responseService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid RegisterRequest request) {
        Map<String, Object> resultMap = new HashMap<>();

        //檢查帳號是否已存在
        String userId = request.getUserId();
        if (registerService.checkUserIdIsUsed(userId)) {
            String errorMessage = messageService.getLocaleMessage(
                    "register.controller.id.already.exists",
                    new String[]{userId});
            return responseService.fail(resultMap, HttpStatus.NOT_FOUND.value(), errorMessage);
        }

        try {
            registerService.register(request);
            return responseService.ok(resultMap);
        } catch (Exception e) {
            return responseService.fail(resultMap, HttpStatus.NOT_FOUND.value(), "註冊失敗！");
        }
    }
}
