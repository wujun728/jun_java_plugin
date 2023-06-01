package org.simple.web.jwt.config;

import org.simple.web.jwt.filter.JwtAuthenticationTokenFilter;
import org.simple.web.jwt.filter.UserLoginFilter;
import org.simple.web.jwt.handler.SimpleAuthenticatingFailureHandler;
import org.simple.web.jwt.handler.SimpleAuthenticatingSuccessHandler;
import org.simple.web.jwt.property.JwtAuthFilterProperty;
import org.simple.web.jwt.property.SimpleSecurityProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

/**
 * 项目名称：web-web-jwt
 * 类名称：WebSecurityConfig
 * 类描述：WebSecurityConfig Spring Security 配置
 * 创建时间：2018/4/11 16:48
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"org.simple.web.jwt"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthFilterProperty jwtAuthFilterProperty;

    @Autowired
    private SimpleSecurityProperty simpleSecurityProperty;

    @Autowired
    private SimpleAuthenticatingSuccessHandler simpleAuthenticatingSuccessHandler;

    @Autowired
    private SimpleAuthenticatingFailureHandler simpleAuthenticatingFailureHandler;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * HTTP请求安全处理
     * token请求授权
     *
     * @param httpSecurity .
     * @throws Exception .
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 由于使用的是JWT，我们这里不需要csrf
        httpSecurity.csrf().disable();
        // 基于token，所以不需要session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 设置允许匿名访问的路由
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry
                = httpSecurity.authorizeRequests();
        String[] urls = jwtAuthFilterProperty.getExceptUrl().split(",");
        for (String url : urls) {
            if (!StringUtils.isEmpty(url)) {
                expressionInterceptUrlRegistry.antMatchers(url).permitAll();
            }
        }
        expressionInterceptUrlRegistry
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加JWT filter
        //将token验证添加在密码验证前面
        httpSecurity.addFilterBefore(getJwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(getUserLoginFilter(), JwtAuthenticationTokenFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactory.getInstance(simpleSecurityProperty.getPasswordEncoder());
    }

    @Bean
    public AuthenticationManager getManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter getJwtAuthenticationTokenFilter() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public UserLoginFilter getUserLoginFilter() throws Exception {
        UserLoginFilter userLoginFilter = new UserLoginFilter(getManagerBean());
        userLoginFilter.setAuthenticationSuccessHandler(simpleAuthenticatingSuccessHandler);
        userLoginFilter.setAuthenticationFailureHandler(simpleAuthenticatingFailureHandler);
        return userLoginFilter;
    }

}
