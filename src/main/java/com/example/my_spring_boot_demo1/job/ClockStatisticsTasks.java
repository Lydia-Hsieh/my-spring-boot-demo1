package com.example.my_spring_boot_demo1.job;

import com.example.my_spring_boot_demo1.controller.clockRecordController.service.ClockRecordService;
import com.example.my_spring_boot_demo1.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ClockStatisticsTasks {

    @Autowired
    private ClockRecordService clockRecordService;

    /**
     * 使用for迴圈計算打卡時間差
     */
//    @Scheduled(initialDelay = 10, fixedRate = 60, timeUnit = TimeUnit.SECONDS)
//    @Scheduled(cron = "0 40 13 * * TUE-SAT")
    public void calculateAllClockRecords() {
        List<Account> users = clockRecordService.getAllUsers();
        users.forEach(user -> {
            String userId = user.getUserId();
            log.debug("User ID [{}] calculation starts.", userId);
            clockRecordService.countClockStatisticsAsync(userId);
        });
    }

    /**
     * 使用CompletableFuture管理全部async tasks
     */
    @Scheduled(cron = "0 8 * * *")
    public void calculateAllClockRecordsWithCompletableFuture() {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<Account> users = clockRecordService.getAllUsers();

        //非同步計算所有userId的打卡時間差
        List<CompletableFuture<Void>> futures = users.stream()
                .map(user -> {
                    CompletableFuture<Void> future = CompletableFuture
                            .runAsync(() -> clockRecordService
                                    .countClockStatistics(user.getUserId()), executorService);
                    return future;
                }).toList();

        //等待futures裡的CompletableFuture全部完成後打印log
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> log.debug("工作完成！"));
    }
}
