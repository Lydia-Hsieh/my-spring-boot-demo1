package com.example.my_spring_boot_demo1.controller.loginController.pojo;

import com.example.my_spring_boot_demo1.entity.Account_Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {

    private String userId;
    private String name;

    public LoginDto(Account_Entity accountEntity) {
        this.userId = accountEntity.getUserId();
        this.name = accountEntity.getName();
    }
}
