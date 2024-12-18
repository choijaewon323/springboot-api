package com.project.crud.aop;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @PostConstruct
    public void init() {
        log.info("LogAspect successfully created");
    }

    @Around("execution( * com.project.crud..*ApiController.*(..) )")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("{}: {}ms", joinPoint.getSignature(), (end - start));

        return result;
    }
}
