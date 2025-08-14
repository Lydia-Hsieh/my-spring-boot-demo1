package com.example.my_spring_boot_demo1.dao.repository;

import com.example.my_spring_boot_demo1.entity.TestPropagationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestPropagationRepository extends JpaRepository<TestPropagationEntity, Integer> {
}
