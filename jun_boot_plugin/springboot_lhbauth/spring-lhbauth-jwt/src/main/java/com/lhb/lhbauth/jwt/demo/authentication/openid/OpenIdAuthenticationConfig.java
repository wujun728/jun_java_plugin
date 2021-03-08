package com.lhb.lhbauth.jwt.demo.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @description
 * @date 2019/1/8 0008 14:54
 */
@Component
public class OpenIdAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository connectionRepository;


    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Bean
    public AppSignUpUtils appSignUpUtils(){
        return new AppSignUpUtils(redisTemplate, connectionRepository, connectionFactoryLocator);
    }



    @Override
    public void configure(HttpSecurity http) throws Exception {
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();

        //设置AuthenticationManager
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        //设置成功失败处理器
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);


        //设置provider
        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setMyUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(connectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
