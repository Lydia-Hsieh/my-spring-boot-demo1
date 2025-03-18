package com.example.my_spring_boot_demo1.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * call有@Async的方法會用到這個Executor，用Completable沒傳入Executor會用預設的ForkJoinPool.commonPool()
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8); //限制async task數量為8個
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("ClockRecords-");
        executor.initialize();
        return executor;
    }
}
