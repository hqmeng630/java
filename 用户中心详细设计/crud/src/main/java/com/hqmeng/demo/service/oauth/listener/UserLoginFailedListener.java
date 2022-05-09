package com.hqmeng.demo.service.oauth.listener;

import com.alibaba.fastjson.JSONObject;
import es.moki.ratelimitj.core.limiter.request.RequestLimitRule;
import es.moki.ratelimitj.core.limiter.request.RequestRateLimiter;
import es.moki.ratelimitj.inmemory.request.InMemorySlidingWindowRequestRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用户登录失败监听器
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.oauth2.handler.ApplicationListenerAuthencationSuccess
 * @Date: 2019/7/25 11:27
 * @Version: 1.0
 */
@Slf4j
@Component
public class UserLoginFailedListener implements ApplicationListener<UserLoginFailedEvent> {
    //错误了第四次返回true,然后锁定账号,第五次即使密码正确也会报账户锁定
    Set<RequestLimitRule> rules = Collections.singleton(RequestLimitRule.of(1, TimeUnit.MINUTES, 3)); // 3 request per 1 minute, per key
    RequestRateLimiter limiter = new InMemorySlidingWindowRequestRateLimiter(rules);
    @Override
    public void onApplicationEvent(UserLoginFailedEvent event) {
        if (event.getSource() instanceof Authentication) {
            Authentication authentication = (Authentication) event.getSource();
            log.info(JSONObject.toJSONString(authentication));

            boolean reachLimit = limiter.overLimitWhenIncremented(authentication.getName());
            if (reachLimit) {
                throw new LockedException("账户被锁定");
            }
        }

        System.out.println("----用户验证信息---faile----------------------");

    }
}
