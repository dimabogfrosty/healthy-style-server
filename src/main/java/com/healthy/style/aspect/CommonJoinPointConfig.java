package com.healthy.style.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("within(@com.healthy.style.aspect.annotation.LogExecutionTime *)" +
            " || @annotation(com.healthy.style.aspect.annotation.LogExecutionTime)")
    public void logExecutionTime() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.get*(..))" +
            " && within(@org.springframework.stereotype.Service *)")
    public void serviceGetLayer() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.save*(..))" +
            " && within(@org.springframework.stereotype.Service *)")
    public void serviceSaveLayer() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.delete*(..))" +
            " && within(@org.springframework.stereotype.Service *)")
    public void serviceDeleteLayer() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " && within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerGetLayer() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " && within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPostLayer() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            " && within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPutLayer() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " && within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerDeleteLayer() {
    }

    @Pointcut("within(com.healthy.style..*)")
    public void exceptionLayer() {
    }
}
