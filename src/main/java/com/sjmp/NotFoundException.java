package com.sjmp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//这是一个自定义的类 extends RuntimeException
//@ResponseStatus HttpStatus.NOT_FOUND 为状态码，标记当前资源为找不到状态，跳转到对应状态码的页面(404)。
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
