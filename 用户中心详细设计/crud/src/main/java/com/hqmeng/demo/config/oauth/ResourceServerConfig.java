package com.hqmeng.demo.config.oauth;

import com.hqmeng.demo.config.oauth.error.MyAccessDeniedHandler;
import com.hqmeng.demo.config.oauth.error.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //这里的uri不需要带server.servlet.context-path配置的前缀
                .requestMatchers().antMatchers("/user/**", "/oauth/**")
                .and()
                .authorizeRequests()
                .anyRequest().access("@myAccessService.hasPermission(request,authentication)")
        ;
    }

    /**
     * 让给spel表达式中的bean可以成功注入
     * 解决 org.springframework.expression.spel.SpelEvaluationException: EL1057E: No bean resolver registered in the context to resolve access to bean
     *
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(oAuth2WebSecurityExpressionHandler)
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .accessDeniedHandler(new MyAccessDeniedHandler())
        ;
    }
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}