package com.sjmp.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 10:25
 * @description: 配置需要拦截的页面
 */

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        将定义的那张网加载进来,拦截 admin 下的访问路径，排除/admin,/admin/login,login 需要提交 form 表单
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login");
    }
}
