package com.example.my_spring_boot_demo1.controller.changePwController.service;

import com.example.my_spring_boot_demo1.controller.changePwController.pojo.ChangePwVo;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.entity.Account;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChangePwService {

    @Autowired
    private AccountRepository accountRepository;

    public void changePw(ChangePwVo vo) throws Exception {
        Optional<Account> accountOptional = accountRepository.findByUserId(vo.getUserId());
        if (accountOptional.isEmpty()) {
            throw new Exception("該帳號不存在！");
        }

        Account account = accountOptional.get();
        //密碼以BCrypt加密
        String hashedPw = BCrypt.hashpw(vo.getPwToChange(), BCrypt.gensalt());
        account.setPassword(hashedPw);
        accountRepository.save(account);
    }
}
