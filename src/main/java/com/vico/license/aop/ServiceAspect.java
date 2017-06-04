package com.vico.license.aop;

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

    public ServiceAspect(){
        System.out.println(">>>>>>>>>>");
    }

    @Pointcut("execution(* com.vico.license.service.*.*.*(..))")
    public void cutService(){}

    @Before("cutService()")
    public void printAop(JoinPoint point){
        System.out.println("AOP 拦截 service 调用!!!");
    }
}
