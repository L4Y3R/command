package com.demo.command.aspect;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @PostConstruct
    public void logApplicationStart() {
        logger.info("Application started at: {}", getCurrentTimestamp());
    }

    @PreDestroy
    public void logApplicationEnd() {
        logger.info("Application shutting down at: {}", getCurrentTimestamp());
    }

    @Before("execution(* com.example.demo..*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Starting execution of method '{}': {}", methodName, getCurrentTimestamp());
    }

    @After("execution(* com.example.demo..*.*(..))")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Completed execution of method '{}': {}", methodName, getCurrentTimestamp());
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
