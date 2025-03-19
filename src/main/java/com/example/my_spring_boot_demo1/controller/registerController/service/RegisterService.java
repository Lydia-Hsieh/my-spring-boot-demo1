package com.example.my_spring_boot_demo1.controller.registerController.service;

import com.example.my_spring_boot_demo1.controller.registerController.pojo.RegisterRequest;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void register(RegisterRequest request) {
        Account_Entity accountEntity = new Account_Entity();
        accountEntity.setUserId(request.getUserId());
        accountEntity.setName(request.getName());

        //密碼以BCrypt加密
        String hashedPw = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        accountEntity.setPassword(hashedPw);

        accountRepository.save(accountEntity);
    }

    public boolean checkUserIdIsUsed(String userId) {
        Optional<Account_Entity> accountOptional = accountRepository.findByUserId(userId);
        return accountOptional.isPresent();
    }
}
