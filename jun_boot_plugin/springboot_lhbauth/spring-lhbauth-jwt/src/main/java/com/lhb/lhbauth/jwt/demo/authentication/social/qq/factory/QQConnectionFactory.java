package com.lhb.lhbauth.jwt.demo.authentication.social.qq.factory;

import com.lhb.lhbauth.jwt.demo.authentication.social.qq.service.QQService;
import com.lhb.lhbauth.jwt.demo.authentication.social.qq.provider.QQServiceProvider;
import com.lhb.lhbauth.jwt.demo.authentication.social.qq.apapter.QQAdapter;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author Wujun
 * @description QQ连接工厂
 * @date 2019/1/3 0003 10:24
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQService> {

    /**
     * 创建连接工厂
     *
     * @param providerId 服务提供商
     * @param appId      serviceProvider
     * @param appSecret  apiAdapter
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());


    }
}
