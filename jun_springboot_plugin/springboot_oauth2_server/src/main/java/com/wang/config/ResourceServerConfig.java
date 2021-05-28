package com.wang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.header.HeaderWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ResourceServerConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    protected ResourceServerConfiguration BataResources() {

        ResourceServerConfiguration resource =  new ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Arrays.<ResourceServerConfigurer>asList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                TokenStore tokenStore = new JdbcTokenStore(dataSource);
                resources.resourceId("bata-services").tokenStore(tokenStore).stateless(true);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        .and()
                        .requestMatchers().antMatchers("/bata/**")
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/bata/**").access("#oauth2.hasScope('read')")
                        .antMatchers(HttpMethod.POST, "/bata/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.PATCH, "/bata/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.PUT, "/bata/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.DELETE, "/bata/**").access("#oauth2.hasScope('write')")
                        .and()
                        .headers().addHeaderWriter(new HeaderWriter() {
                    @Override
                    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
                        response.addHeader("Access-Control-Allow-Origin", "*");
                        if (request.getMethod().equals("OPTIONS")) {
                            response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                        }
                    }
                });
            }

        }));
        resource.setOrder(3);

        return resource;

    }

    @Bean
    protected ResourceServerConfiguration UserResources() {

        ResourceServerConfiguration resource = new ResourceServerConfiguration() {
            // Switch off the Spring Boot @Autowired configurers
            public void setConfigurers(List<ResourceServerConfigurer> configurers) {
                super.setConfigurers(configurers);
            }
        };

        resource.setConfigurers(Arrays.<ResourceServerConfigurer>asList(new ResourceServerConfigurerAdapter() {

            @Override
            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
                TokenStore tokenStore = new JdbcTokenStore(dataSource);
                resources.resourceId("user-services").tokenStore(tokenStore).stateless(true);
            }

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        .and()
                        .requestMatchers().antMatchers("/user/**")
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.GET, "/user/**").access("#oauth2.hasScope('read')")
                        .antMatchers(HttpMethod.POST, "/user/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.PATCH, "/user/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.PUT, "/user/**").access("#oauth2.hasScope('write')")
                        .antMatchers(HttpMethod.DELETE, "/user/**").access("#oauth2.hasScope('write')")
                        .and()
                        .headers().addHeaderWriter(new HeaderWriter() {
                    @Override
                    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
                        response.addHeader("Access-Control-Allow-Origin", "*");
                        if (request.getMethod().equals("OPTIONS")) {
                            response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                        }
                    }
                });
            }

        }));
        resource.setOrder(4);

        return resource;

    }

}