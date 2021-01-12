package com.sjmp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * @Author SJMP1573
 * @Date 2020/9/27 20:15
 * @Description log aop
 * @Since version-1.0
 */

// 标记这是一个切面，可以使用切面的功能
// 托管给 SpringBoot
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    定义一个切面，web 包下的控制器都会拦截！
    @Pointcut("execution(* com.sjmp.web.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){ // JoinPoint 获取类名和方法名
//        获取 HttpServletRequest. 获取 ip / url
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();

        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request : {} ",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("---------doAfter----------");
    }

//    返回方法的结果！
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}" , result);
    }


//    将请求者的相关信息封装成一个对象！
    private class  RequestLog{
//        url 和 ip 可以通过 HttpServletRequest 获取！
        private String url;
        private  String ip;
        private  String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
