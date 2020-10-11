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

// 拦截有 Controller 注解的
@ControllerAdvice
public class ControllerExceptionHandler {
    //获取日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    返回一个错误页面
//    @ExceptionHandler 标识这个方法可做异常信息处理，标注才起作用
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        // 记录出错的 URL,e 控制台会输出日志信息
        logger.error("Requst UPR :{},Exception : {}",request.getRequestURI(),e);

        // 当NotFoundException 标记了状态码NOT_FOUND,就不拦截到error。如果是404、500 等错误，交给springboot 去处理
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView mv = new ModelAndView();
//       前端页面 URL 展示的信息，出错的 URL ，和异常信息
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        // 设置出错时弹出的提示页面。
        mv.setViewName("error/error.html");
        return null;
    }
}
