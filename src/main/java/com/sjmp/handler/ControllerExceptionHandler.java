package com.sjmp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author SJMP1573
 * @Date 2020/9/27 19:19
 * @Description 拦截错误返回到对应的页面
 * @Since version-1.0
 */

// 拦截有 Controller 注解的控制器，例如 IndexController
@ControllerAdvice
public class ControllerExceptionHandler {

    //记录异常，获取日志对象！
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    这个方法 exceptionHandler 返回一个错误页面，并附带一些错误信息（后端输送到前端！）
    接收参数，HttpServletRequest request,从这个参数可以知道访问出错的链接，处理传递过来的异常参数
    @ExceptionHandler 标识这个方法可做异常信息处理，标注才起作用,Exception.class(是所有异常信息的父类) 代表所有的异常信息都可以拦截！
    */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        // 记录出错的 URL,e 控制台会输出日志信息。{}占位的作用！
        logger.error("Request UPR :{},Exception : {}",request.getRequestURI(),e);

//        判断当前异常是否含有状态码，如果含有就交给 SpringBoot 处理，跳转到指定状态码的页面！
        // 当NotFoundException 标记了状态码NOT_FOUND,就不拦截到error。如果是404、500 等错误，交给springboot 去处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

//        返回一个错误的页面，所以要 new 一个 ModelAndView 对象！
        ModelAndView mv = new ModelAndView();
//       前端页面 获取 出错的 URL 的异常信息
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);

        // 设置出错时，返回到哪个页面。
        mv.setViewName("error/error.html");
        return mv;
    }
}
