package com.healthy.style.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogControllerAspect {

    @Before("com.healthy.style.aspect.CommonJoinPointConfig.controllerGetLayer()")
    public void controllerGetLayer(JoinPoint joinPoint) {
        log.info("Controller HTTP GET Method: '{}' Class: '{}'",
                joinPoint.getSignature(),
                joinPoint.getSignature().getDeclaringTypeName());
    }

    @Before("com.healthy.style.aspect.CommonJoinPointConfig.controllerPostLayer()")
    public void controllerPostLayer(JoinPoint joinPoint) {
        log.info("Controller HTTP POST Method: '{}' Class: '{}'",
                joinPoint.getSignature(),
                joinPoint.getSignature().getDeclaringTypeName());
    }

    @Before("com.healthy.style.aspect.CommonJoinPointConfig.controllerPutLayer()")
    public void controllerPutLayer(JoinPoint joinPoint) {
        log.info("Controller HTTP PUT Method: '{}' Class: '{}'",
                joinPoint.getSignature(),
                joinPoint.getSignature().getDeclaringTypeName());
    }

    @Before("com.healthy.style.aspect.CommonJoinPointConfig.controllerDeleteLayer()")
    public void controllerDeleteLayer(JoinPoint joinPoint) {
        log.info("Controller HTTP DELETE Method: '{}' Class: '{}'",
                joinPoint.getSignature(),
                joinPoint.getSignature().getDeclaringTypeName());
    }
}
