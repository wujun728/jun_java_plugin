package com.jun.plugin.oauth2.jwt.demo.authentication.social.qq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

import com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.factory.QQConnectionFactory;
import com.jun.plugin.oauth2.jwt.demo.properties.SecurityProperties;

/**
 * @author Wujun
 * @description
 * @date 2019/1/3 0003 11:01
 */
@Configuration
@ConditionalOnProperty(prefix = "system.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {


    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }
}
