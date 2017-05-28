package com.vico.license.filters;

import com.vico.license.aop.WebContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Liu.Dun
 *         用于管理webcontext对象的生命周期
 */
public class WebContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        WebContext.init(request, response);
        try {
            chain.doFilter(request, response);
        } finally {
            WebContext.destroy();
        }

    }

    @Override
    public void destroy() {

    }

}
