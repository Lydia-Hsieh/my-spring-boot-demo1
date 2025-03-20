package com.example.my_spring_boot_demo1.controller.clockRecordController.service;

import com.example.my_spring_boot_demo1.controller.clockRecordController.pojo.ClockRecordRequest;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.dao.repository.ClockRecordRepository;
import com.example.my_spring_boot_demo1.dao.repository.ClockStatisticsRepository;
import com.example.my_spring_boot_demo1.entity.Account_Entity;
import com.example.my_spring_boot_demo1.entity.ClockRecord_Entity;
import com.example.my_spring_boot_demo1.entity.ClockStatistics_Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ClockRecordService {

    @Autowired
    private ClockRecordRepository clockRecordRepository;

    @Autowired
    private ClockStatisticsRepository clockStatisticsRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 打卡
     * @param request
     */
    @Transactional
    public void clockInAndOut(ClockRecordRequest request) {
        ClockRecord_Entity record = new ClockRecord_Entity();
        record.setUserId(request.getUserId());
        record.setCreateTime(request.getCreateTime());
        record.setRemark(request.getRemark());
        clockRecordRepository.save(record);
    }

    @Transactional
    public void clockInAndOutForFail(ClockRecordRequest request) {
        clockInAndOut(request);
        int j = 1/0; //create situation that must be fail
    }

    /**
     * 統計 user 打卡時間差
     */
    @Transactional
    public ClockStatistics_Entity countClockStatistics(String userId) {
        ClockStatistics_Entity statistics = new ClockStatistics_Entity();
        statistics.setUserId(userId);
        LocalDate recordDay = LocalDate.now().minusDays(1); //計算前一天的打卡時間差
        statistics.setRecordDay(recordDay);

        Optional<ClockRecord_Entity> earliestOptional = clockRecordRepository
                .findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeAsc(
                        userId,
                        recordDay.atStartOfDay(),
                        recordDay.atTime(LocalTime.MAX));
        if (earliestOptional.isEmpty()) {
            statistics.setTimeDiff(BigDecimal.ZERO);
            statistics.setCreateTime(LocalDateTime.now());
            statistics.setRemark("本日未打卡");
            return clockStatisticsRepository.save(statistics);
        }

        LocalDateTime earliestTime = earliestOptional.get().getCreateTime();
        LocalDateTime latestTime = clockRecordRepository
                .findFirstByUserIdAndCreateTimeBetweenOrderByCreateTimeDesc(
                        userId,
                        recordDay.atStartOfDay(),
                        recordDay.atTime(LocalTime.MAX))
                .get().getCreateTime();

        //計算打卡時間差
        Duration diff = Duration.between(earliestTime, latestTime);
        BigDecimal diffHour = BigDecimal.valueOf(diff.toSeconds() / 3600.0)
                .setScale(2, RoundingMode.HALF_UP); //四捨五入

        statistics.setEarliestTime(earliestTime);
        statistics.setLatestTime(latestTime);
        statistics.setTimeDiff(diffHour);
        statistics.setCreateTime(LocalDateTime.now());
        return clockStatisticsRepository.save(statistics);
    }

    @Async
    public CompletableFuture<ClockStatistics_Entity> countClockStatisticsAsync(String userId) {
        log.debug("Thread: {}", Thread.currentThread().getName());
        ClockStatistics_Entity statistics = countClockStatistics(userId);
        return CompletableFuture.completedFuture(statistics);
    }

    public List<Account_Entity> getAllUsers() {
        return accountRepository.findAll();
    }
}
