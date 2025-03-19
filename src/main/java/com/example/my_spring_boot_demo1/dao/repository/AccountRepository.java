package com.example.my_spring_boot_demo1.dao.repository;

import com.example.my_spring_boot_demo1.entity.Account_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account_Entity, String> {

    Optional<Account_Entity> findByUserId(String userId);
}
