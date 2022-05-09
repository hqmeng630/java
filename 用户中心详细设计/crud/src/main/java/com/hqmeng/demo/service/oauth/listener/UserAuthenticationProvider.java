package com.hqmeng.demo.service.oauth.listener;

import com.hqmeng.demo.service.oauth.MyUserDetailsService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Description: 用户自定义身份认证
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.provider.MyAuthenticationProvider
 * @Date: 2019/7/2 17:17
 * @Version: 1.0
 */
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;


    /**
     * @Description 认证处理，返回一个Authentication的实现类则代表认证成功，返回null则代表认证失败
     * @Date 2019/7/5 15:19
     * @Version  1.0
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isBlank(username)){
            throw new OAuth2Exception("username用户名不可以为空");
        }
        if(StringUtils.isBlank(password)){
            throw new OAuth2Exception("密码不可以为空");
        }
        //获取用户信息
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!passwordEncoder.matches(password, user.getPassword())) {
            //发布密码不正确事件
            publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new OAuth2Exception("用户名或者密码错误");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }
    /**
     * @Description 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @Date 2019/7/5 15:18
     * @Version  1.0
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

}
