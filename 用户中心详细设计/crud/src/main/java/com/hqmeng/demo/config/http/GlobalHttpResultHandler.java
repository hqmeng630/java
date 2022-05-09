package com.hqmeng.demo.config.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalHttpResultHandler implements ResponseBodyAdvice {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String uri = serverHttpRequest.getURI().getPath();
        log.info(serverHttpRequest.getURI().getPath());
        if(uri.equals(contextPath+"/oauth/token")) {
            return o;
        }

        if (o instanceof Result) {
            return o;
        }
        return Result.success(o);
    }
}
