package com.vico.license.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by liudun on 2017/6/4.
 */
@Aspect
@Component
public class ServiceAspect {
    private static final Logger LOGGER = Logger.getLogger(ServiceAspect.class);

    @Pointcut("execution(* com.vico.license.service.*.*.*(..))")
    public void cutService(){}

    @Before("cutService()")
    public void printAop(JoinPoint point){
        LOGGER.info("AOP 拦截 service 调用!!!");
    }
}
