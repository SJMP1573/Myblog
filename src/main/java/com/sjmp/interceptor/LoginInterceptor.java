package com.sjmp.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 10:11
 * @description: 定义一个拦截器 ，拦截 admin 路径下的访问路径，相当于给他拉一张网
 */


public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 如果页面请求中返回的 user 为空，说明用户未登录，重定向到登录页面。
        if(request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
        }
        return true;
    }
}
