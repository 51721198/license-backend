package com.vico.license.aop;

import com.vico.license.filters.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Liu.Dun
 *         Aop-安全令牌拦截检查通知
 */

@Aspect
@Component
public class SecurityAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);
    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    @Autowired
    private TokenManager tokenManager;

    private String tokenName;

    //这个切点只拦截带有@NeedCheck注解的方法
    @Pointcut("@annotation(com.vico.license.aop.NeedCheck)")
    public void needAnnotation() {

    }

    //这个切点拦截所有除了BounceController之外所有其他controller中的所有方法
//    @Pointcut("execution(* com.vico.license.controller.*.*(..)) && !bean(bounceController)")
    //这个切点拦截打到所有cotroller的请求
                    //这个*代表任意返回值             类名.方法(..)两点表示任意参数
    @Pointcut("execution(* com.vico.license.controller.*.*(..))")
    public void cutMethodRequest(){}

//    @Before("needAnnotation()")
    @Before("cutMethodRequest()")
    public void before(JoinPoint joinPoint) throws Throwable {    //Before型通知只能用JoinPoint,不能用ProceedingJoinPoint
    }


    //只有添加了@needAnnotation注解的contorller中的方法才会被拦截
    @Around("needAnnotation() && cutMethodRequest()")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.info("=====token安全验证!!!=====");
        //从切点上面获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        //若目标方法忽略了安全性检查,则直接调用目标方法(就是说若目标方法加了ignore注解就不拦截了)
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }

        //从request header中获取当前token
        System.out.println(WebContext.getRequest());
        if (StringUtils.isBlank(tokenName)) tokenName = DEFAULT_TOKEN_NAME;
        String token = WebContext.getRequest().getHeader(tokenName);

        //检查token的有效性
        if (!tokenManager.checkToken(token)) {
            LOGGER.warn("=====token验证失败=====");
            String message = String.format("token [%s] is invalid", token);
            throw new TokenException(message);
        }

        //检查通过则调用目标方法
        return pjp.proceed();
    }

}
