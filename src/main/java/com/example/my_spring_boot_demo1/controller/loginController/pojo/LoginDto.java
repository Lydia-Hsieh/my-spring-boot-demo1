package com.example.my_spring_boot_demo1.controller.loginController.pojo;

import com.example.my_spring_boot_demo1.entity.Account;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {

    private String userId;
    private String name;

    public LoginDto(Account account) {
        this.userId = account.getUserId();
        this.name = account.getName();
    }
}
