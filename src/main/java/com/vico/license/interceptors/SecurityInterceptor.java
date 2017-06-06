package com.vico.license.interceptors;

import com.vico.license.aop.IgnoreSecurity;
import com.vico.license.aop.TokenException;
import com.vico.license.aop.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 *
 *spring-mvc拦截器,配置文件在springmvc-servlet当中
 */

public class SecurityInterceptor extends HandlerInterceptorAdapter {

    public static final Logger LOGGER = Logger.getLogger(SecurityInterceptor.class);

    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    private TokenManager tokenManager;
    private String tokenName;

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        if (StringUtils.isEmpty(tokenName)) {
            tokenName = DEFAULT_TOKEN_NAME;
        }
        this.tokenName = tokenName;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //从切点上获取目标方法

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果目标方法忽略了安全性检查,则直接调用目标的方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }

        //从request header 中获取当前token
        String token = request.getHeader(tokenName);

        //检查token的有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("toke [%s] is invalid", token);
            throw new TokenException(message);
        }

        LOGGER.info("拦截器验证通过");

        //调用目标方法
        return true;
    }


}
