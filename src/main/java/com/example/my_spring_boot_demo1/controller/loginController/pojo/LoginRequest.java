package com.example.my_spring_boot_demo1.controller.loginController.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
