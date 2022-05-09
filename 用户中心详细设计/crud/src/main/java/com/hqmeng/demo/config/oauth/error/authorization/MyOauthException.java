package com.hqmeng.demo.config.oauth.error.authorization;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author zhanghui
 * @date 2019/6/21
 */
@JsonSerialize(using = MyOauthExceptionSerializer.class)
public class MyOauthException extends OAuth2Exception {
    public MyOauthException(String msg) {
        super(msg);
    }
}
