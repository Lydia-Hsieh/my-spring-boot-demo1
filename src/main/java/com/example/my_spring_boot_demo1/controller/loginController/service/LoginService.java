package com.example.my_spring_boot_demo1.controller.loginController.service;

import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginRequest;
import com.example.my_spring_boot_demo1.dao.mybatis.AccountMapper;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 登入時檢查帳號密碼是否正確
     * @param request
     * @param message 要回傳給user的錯誤訊息
     * @return
     */
    public boolean checkUserIdAndPw(LoginRequest request, StringBuilder message) {
        boolean result = false;
        message.append("帳號或密碼錯誤！");
        //帳號存在 且 密碼正確，回傳true
        Optional<Account_Entity> accountOptional = accountMapper.getOneByUserId(request.getUserId());
        if (accountOptional.isPresent()) {
            Account_Entity accountEntity = accountOptional.get();
            //確認vo的密碼與DB裡的密碼吻合
            result = BCrypt.checkpw(request.getPassword(), accountEntity.getPassword());
        }
        return result;
    }
}
