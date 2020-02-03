package com.healthy.style.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("within(@com.healthy.style.aspect.annotation.LogExecutionTime *)" +
            " || @annotation(com.healthy.style.aspect.annotation.LogExecutionTime)")
    public void logExecutionTime() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.get*(..))")
    public void serviceGetLayer() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.save*(..))")
    public void serviceSaveLayer() {
    }

    @Pointcut("execution(* com.healthy.style.service.*.delete*(..))")
    public void serviceDeleteLayer() {
    }
}
