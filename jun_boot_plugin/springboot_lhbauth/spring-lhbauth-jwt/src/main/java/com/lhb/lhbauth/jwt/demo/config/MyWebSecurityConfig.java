package com.lhb.lhbauth.jwt.demo.config;

import com.lhb.lhbauth.jwt.demo.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.lhb.lhbauth.jwt.demo.authentication.mobile.SmsCodeFilter;
import com.lhb.lhbauth.jwt.demo.authentication.openid.OpenIdAuthenticationConfig;
import com.lhb.lhbauth.jwt.demo.cahe.redis.VcodeManager;
import com.lhb.lhbauth.jwt.demo.constants.FromLoginConstant;
import com.lhb.lhbauth.jwt.demo.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author Wujun
 * @description 浏览器配置
 * @date 2018/12/25 0025 10:53
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private VcodeManager vcodeManager;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private OpenIdAuthenticationConfig openIdAuthenticationConfig;
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;


//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    /**
     * 生成记得我的token
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        //使用jdbc来存储
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //当为true的时候就会自动创建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(vcodeManager);
        smsCodeFilter.setSecurityProperties(securityProperties);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                .formLogin().loginPage(FromLoginConstant.LOGIN_PAGE).loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL)
                //登录成功之后的处理
                .successHandler(myAuthenticationSuccessHandler)
                //允许访问
                .and().authorizeRequests().antMatchers(
                FromLoginConstant.LOGIN_PROCESSING_URL,
                FromLoginConstant.LOGIN_PAGE,
                securityProperties.getOauthLogin().getOauthLogin(),
                securityProperties.getOauthLogin().getOauthGrant(),
                "/myLogout",
                "/code/sms")
//                "/oauth/**")
                .permitAll().anyRequest().authenticated()
                //禁用跨站伪造
                .and().csrf().disable()
                //短信验证码配置
                .apply(smsCodeAuthenticationSecurityConfig)
                //社交登录
                .and().apply(mySocialSecurityConfig)
                //openID登录
                .and().apply(openIdAuthenticationConfig);

    }


}
