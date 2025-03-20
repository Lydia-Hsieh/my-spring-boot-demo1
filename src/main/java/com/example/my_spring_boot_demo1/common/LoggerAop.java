package com.example.my_spring_boot_demo1.common;

import com.example.my_spring_boot_demo1.entity.Account_Entity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggerAop {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(public * com.example.my_spring_boot_demo1.controller..*Controller.*(..))")
    public void pointcutController() {
    }

    @Before(value = "pointcutController()")
    public void generateControllerAccessLog(JoinPoint joinPoint) {
        //access time
        String accessTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //who
        Object account = SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String userId = account instanceof String ? //Spring Security的Principal不存在時會回傳字串"anonymousUser"
                account.toString() : ((Account_Entity)account).getUserId();
        //args
        String args = Arrays.toString(joinPoint.getArgs());
        //do something
        String uri = request.getRequestURI();

        log.info("\nAccess time: {}\nUser: {}\nArgs: {}\nRequest URI: {}",
                accessTime, userId, args, uri);
    }
}
