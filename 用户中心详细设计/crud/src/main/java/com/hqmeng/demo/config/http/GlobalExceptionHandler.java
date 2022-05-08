package com.hqmeng.demo.config.http;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     *
     */
    @ExceptionHandler(value = BizException.class)
    public Result bizExceptionHandler(BizException e) {
        return Result.bizError(e);
    }

    /**
     * 处理其他异常
     *
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler( Exception e) {
        return Result.otherError(e);
    }
}
