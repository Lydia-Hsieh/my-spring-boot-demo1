package com.example.my_spring_boot_demo1.controller.changePwController.service;

import com.example.my_spring_boot_demo1.controller.changePwController.pojo.ChangePwRequest;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import com.example.my_spring_boot_demo1.exception.AccountNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ChangePwService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void changePw(ChangePwRequest request) {
        Optional<Account_Entity> accountOptional = accountRepository.findByUserId(request.getUserId());
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("該帳號不存在！");
        }

        Account_Entity accountEntity = accountOptional.get();
        //密碼以BCrypt加密
        String hashedPw = BCrypt.hashpw(request.getPwToChange(), BCrypt.gensalt());
        accountEntity.setPassword(hashedPw);
        accountRepository.save(accountEntity);
    }
}
