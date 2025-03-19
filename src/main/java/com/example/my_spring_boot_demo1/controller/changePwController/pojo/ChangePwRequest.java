package com.example.my_spring_boot_demo1.controller.changePwController.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwRequest {

    @NotBlank
    private String userId;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()\\[\\]{}<>+\\-*/?,.:;\"'_\\|~`!@#$%^&=]).{8,}$",
             message = "密碼需包含英文字母大小寫、數字以及特殊字元，長度至少有8位數！")
    private String pwToChange;
}
