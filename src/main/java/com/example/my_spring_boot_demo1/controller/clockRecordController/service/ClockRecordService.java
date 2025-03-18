package com.example.my_spring_boot_demo1.controller.clockRecordController.service;

import com.example.my_spring_boot_demo1.controller.clockRecordController.pojo.ClockRecordVo;
import com.example.my_spring_boot_demo1.dao.repository.AccountRepository;
import com.example.my_spring_boot_demo1.dao.repository.ClockRecordRepository;
import com.example.my_spring_boot_demo1.dao.repository.ClockStatisticsRepository;
import com.example.my_spring_boot_demo1.entity.Account;
import com.example.my_spring_boot_demo1.entity.ClockRecord;
import com.example.my_spring_boot_demo1.entity.ClockStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
     * @param vo
     */
    public void clockInAndOut(ClockRecordVo vo) {
        ClockRecord record = new ClockRecord();
        record.setUserId(vo.getUserId());
        record.setCreateTime(vo.getCreateTime());
        record.setRemark(vo.getRemark());
        clockRecordRepository.save(record);
    }

    /**
     * 統計 user 打卡時間差
     */
    public ClockStatistics countClockStatistics(String userId) {
        ClockStatistics statistics = new ClockStatistics();
        statistics.setUserId(userId);
        LocalDate recordDay = LocalDate.now().minusDays(1); //計算前一天的打卡時間差
        statistics.setRecordDay(recordDay);

        Optional<ClockRecord> earliestOptional = clockRecordRepository
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
    public CompletableFuture<ClockStatistics> countClockStatisticsAsync(String userId) {
        log.debug("Thread: {}", Thread.currentThread().getName());
        ClockStatistics statistics = countClockStatistics(userId);
        return CompletableFuture.completedFuture(statistics);
    }

    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }
}
