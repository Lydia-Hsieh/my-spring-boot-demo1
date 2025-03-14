package com.example.my_spring_boot_demo1.controller.changePwController;

import com.example.my_spring_boot_demo1.controller.changePwController.pojo.ChangePwVo;
import com.example.my_spring_boot_demo1.controller.changePwController.service.ChangePwService;
import com.example.my_spring_boot_demo1.util.StringUtil;
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

    @PostMapping("/changePw")
    public ResponseEntity<Map<String, Object>> changePw(@RequestBody ChangePwVo vo) {
        Map<String, Object> resultMap = new HashMap<>();

        //檢核輸入的新密碼格式
        if (!StringUtil.checkPassword(vo.getPwToChange())) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", "密碼需包含英文字母大小寫、數字以及特殊字元，長度至少有8位數！");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }

        try {
            changePwService.changePw(vo);
            resultMap.put("status", true);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            resultMap.put("status", false);
            resultMap.put("errorMessage", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
        }
    }
}
