package com.hqmeng.demo.service.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class MyAccessService {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String token = request.getHeader("Authorization");
        log.info("request.getHeader(\"Authorization\") {}", token);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        //自定义的接口验证时，只需请求头携带token即可
        String selfUri = request.getRequestURI();
        if (selfUri.startsWith(contextPath) && !selfUri.contains("/processing/user/oauth/verify_token")) {
            log.info("contextPath: {}", contextPath);
            return true;
        }

        String method = request.getParameter("method");
        String uri = request.getParameter("uri");
        String authority = method + ";" + uri;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = ((UserDetails) principal);
            SimpleGrantedAuthority simpleGrantedAuthority
                    = new SimpleGrantedAuthority(authority);
            //判断用户已授权访问的资源中，是否包含“本次请求的资源”
            return userDetails.getAuthorities().contains(simpleGrantedAuthority);
        }
        return false;
    }

}
