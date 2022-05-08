package com.hqmeng.demo.config.oauth.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqmeng.demo.config.http.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghui
 * @date 2019/6/21
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> map = new HashMap<>();
        map.put("access",false);
        map.put("desc",accessDeniedException.getMessage());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), Result.success(map));
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
