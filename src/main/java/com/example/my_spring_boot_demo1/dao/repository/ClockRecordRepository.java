package com.example.my_spring_boot_demo1.dao.repository;

import com.example.my_spring_boot_demo1.entity.ClockRecord_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ClockRecordRepository extends JpaRepository<ClockRecord_Entity, String> {

    Optional<ClockRecord_Entity> findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeAsc(String userId, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<ClockRecord_Entity> findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String userId, LocalDateTime dateStart, LocalDateTime dateEnd);
}
