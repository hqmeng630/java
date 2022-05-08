package com.hqmeng.demo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TokenController {
    @Autowired
    ConsumerTokenServices consumerTokenServices;

    @DeleteMapping("/oauth/remove_token")
    public boolean removeToken(Authentication authentication) {
        Object details = authentication.getDetails();
        log.info("details : {}", JSONObject.toJSONString(details));
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(details));
        String tokenValue = jsonObject.getString("tokenValue");
        log.info("tokenValue: {}", tokenValue);
        return consumerTokenServices.revokeToken(tokenValue);

    }

    @GetMapping("/oauth/verify_token")
    public boolean verifyToken(@RequestParam String method, String uri) {
        return true;
    }
}
