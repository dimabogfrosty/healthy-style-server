package com.healthy.style.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogExceptionAspect {

    @AfterThrowing(value = "com.healthy.style.aspect.CommonJoinPointConfig.exceptionLayer()", throwing = "e")
    public void exceptionLayer(JoinPoint joinPoint, Throwable e) {
        log.error("Exception Class: '{}', Method: '{}', name = {}, message = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature(),
                e.getClass().getName(),
                e.getMessage());
    }
}
