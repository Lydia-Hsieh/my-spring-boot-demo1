package com.example.my_spring_boot_demo1.dao.mybatis;

import com.example.my_spring_boot_demo1.entity.Account;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

public interface AccountMapper {

    @Select("SELECT * FROM Account WHERE USER_ID = #{userId}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "userId", column = "USER_ID"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "name", column = "NAME")
    })
    Optional<Account> getOneByUserId(String userId);
}
