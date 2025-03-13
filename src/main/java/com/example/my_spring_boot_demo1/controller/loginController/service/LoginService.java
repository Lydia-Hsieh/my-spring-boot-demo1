package com.example.my_spring_boot_demo1.controller.loginController.service;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginVo;
import com.example.my_spring_boot_demo1.dao.mybatis.AccountMapper;
import com.example.my_spring_boot_demo1.entity.Account;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 登入時檢查帳號密碼是否正確
     * @param vo
     * @param message 要回傳給user的錯誤訊息
     * @return
     */
    public boolean checkUserIdAndPw(LoginVo vo, StringBuilder message) {
        boolean result = false;
        message.append("帳號或密碼錯誤！");
        //帳號存在 且 密碼正確，回傳true
        Optional<Account> accountOptional = accountMapper.getOneByUserId(vo.getUserId());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            //確認vo的密碼與DB裡的密碼吻合
            result = BCrypt.checkpw(vo.getPassword(), account.getPassword());
        }
        return result;
    }
}
