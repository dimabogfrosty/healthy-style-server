package com.healthy.style.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogServiceAspect {

    @After("com.healthy.style.aspect.CommonJoinPointConfig.serviceGetLayer()")
    public void serviceGetLayerExecution(JoinPoint joinPoint) {
        log.info("Get Method: '{}' Class: '{}'",
                joinPoint.getSignature(),
                joinPoint.getThis().toString().split("@")[0]);
    }

    @AfterReturning(value = "com.healthy.style.aspect.CommonJoinPointConfig.serviceSaveLayer()", returning = "result")
    public void serviceSaveLayerExecution(JoinPoint joinPoint, Object result) {
        log.info("Save Return: {} Method: '{}' Class: '{}'",
                result,
                joinPoint.getSignature(),
                joinPoint.getThis().toString().split("@")[0]);
    }

    @After("com.healthy.style.aspect.CommonJoinPointConfig.serviceDeleteLayer()")
    public void serviceDeleteLayer(JoinPoint joinPoint) {
        log.info("Delete entity with id: {} Method: '{}' Class: '{}'",
                joinPoint.getArgs(),
                joinPoint.getSignature(),
                joinPoint.getThis().toString().split("@")[0]);
    }
}
