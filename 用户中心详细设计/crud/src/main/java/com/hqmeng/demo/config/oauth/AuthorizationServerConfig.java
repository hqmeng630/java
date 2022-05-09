package com.hqmeng.demo.config.oauth;

import com.hqmeng.demo.config.oauth.error.authorization.MyWebResponseExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    JwtTokenEnhancer jwtTokenEnhancer;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private TokenStore jdbcTokenStore;
    @Resource
    private ClientDetailsService jdbcClientDetailsService;
    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain chain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        chain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(jdbcTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .reuseRefreshTokens(false)
                .tokenEnhancer(chain)
                //自定义 /oauth/token 异常处理
                .exceptionTranslator(new MyWebResponseExceptionTranslator())
        ;



    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .withClientDetails(jdbcClientDetailsService);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 访问/oauth/token_key, 默认拒绝访问
        security.tokenKeyAccess("permitAll()");
        // 访问 /oauth/check_token, 默认拒绝访问
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
        ;
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置jwt密钥
        jwtAccessTokenConverter.setSigningKey("test_key");
        return jwtAccessTokenConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
