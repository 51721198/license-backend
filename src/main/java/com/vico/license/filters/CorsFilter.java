package com.vico.license.filters;

import com.github.pagehelper.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CorsFilter implements Filter {

    private String allowOrigin;    //允许访问的客户端域名,例如www.douyu.com,若为*,则表示从任意域都能访问,即不做任何限制
    private String allowMethods;   //允许访问的方法名,多个方法名用逗号分隔,比如:GET,POST,PUT
    private String allowCredentials;  //是否允许请求带有验证信息,若要获取客户端域下的cookie时,需要将其设置为true
    private String allowHeaders;     //允许服务端访问的客户端请求头,多个请求头用逗号分隔,例如:Content-type,X-Token
    private String exposeHeaders;     //允许客户端访问的服务端响应头,多个响应头用逗号分割

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");   //filterConfig即为web.xml中对应filter的配置信息
        allowMethods = filterConfig.getInitParameter("allowMethods");
        allowCredentials = filterConfig.getInitParameter("allowCredentials");
        allowHeaders = filterConfig.getInitParameter("allowHeaders");
        exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;       //将servletRequest强制转换为httpServletRequest
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (StringUtil.isNotEmpty(allowOrigin)) {
            List<String> allowOriginList = Arrays.asList(allowOrigin.split(","));   //允许的客户端域名放入一个列表中,以逗号分隔,破解不允许指定多个域名的限制
            if (allowOriginList != null) {
                String currentOrigin = request.getHeader("Origin");
                if (allowOriginList.contains(currentOrigin)) {  //如果请求的域名在允许列表当中,那就给请求加一个跨域的请求头
                    System.out.println("cros can here!!!!!!!!");
                    System.out.println(currentOrigin);
                    response.setHeader("Access-Control-Allow-Origin", currentOrigin);
                }
            }
        }
        if (StringUtil.isNotEmpty(allowMethods)) {
            response.setHeader("Access-Control-Allow-Methods", allowMethods);
        }
        if (StringUtil.isNotEmpty(allowCredentials)) {
            response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
        }
        if (StringUtil.isNotEmpty(allowHeaders)) {
            response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        if (StringUtil.isNotEmpty(exposeHeaders)) {
            response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
