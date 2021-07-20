package com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.provider;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.service.QQService;
import com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.service.impl.QQServiceImpl;
import com.jun.plugin.oauth2.jwt.demo.authentication.social.qq.template.QQOAuth2Template;

/**
 * @author Wujun
 * @description
 * @date 2019/1/3 0003 10:10
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQService> {

    /**
     * 导向authorizeUrl地址
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";

    /**
     * 得到code之后，导向申请获取token
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    private String appId;


    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    @Override
    public QQService getApi(String accessToken) {
        return new QQServiceImpl(accessToken, appId);
    }
}
