package com.example.my_spring_boot_demo1.dao.repository;

import com.example.my_spring_boot_demo1.entity.ClockRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ClockRecordRepository extends JpaRepository<ClockRecord, String> {

    Optional<ClockRecord> findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeAsc(String userId, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<ClockRecord> findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(String userId, LocalDateTime dateStart, LocalDateTime dateEnd);
}
