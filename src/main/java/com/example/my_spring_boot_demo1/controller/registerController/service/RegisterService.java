package com.example.my_spring_boot_demo1.controller.registerController.service;

import com.example.my_spring_boot_demo1.controller.registerController.pojo.AccountVo;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.entity.Account;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private AccountRepository accountRepository;

    public void register(AccountVo vo) {
        Account account = new Account();
        account.setUserId(vo.getUserId());
        account.setName(vo.getName());

        //密碼以BCrypt加密
        String hashedPw = BCrypt.hashpw(vo.getPassword(), BCrypt.gensalt());
        account.setPassword(hashedPw);

        accountRepository.save(account);
    }

    public boolean checkUserIdIsUsed(String userId) {
        Optional<Account> accountOptional = accountRepository.findByUserId(userId);
        return accountOptional.isPresent();
    }
}
