package com.example.my_spring_boot_demo1.dao.repository;

import com.example.my_spring_boot_demo1.entity.ClockStatistics_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockStatisticsRepository extends JpaRepository<ClockStatistics_Entity, String> {
}
