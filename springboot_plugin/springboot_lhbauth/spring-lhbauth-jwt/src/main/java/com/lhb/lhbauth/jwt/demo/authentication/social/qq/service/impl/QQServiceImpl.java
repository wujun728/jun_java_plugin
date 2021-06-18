package com.lhb.lhbauth.jwt.demo.authentication.social.qq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhb.lhbauth.jwt.demo.authentication.social.qq.model.QQUserInfo;
import com.lhb.lhbauth.jwt.demo.authentication.social.qq.service.QQService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * @author Wujun
 * @description
 * @date 2019/1/3 0003 9:59
 */
@Slf4j
public class QQServiceImpl extends AbstractOAuth2ApiBinding implements QQService {

    /**
     * 获取openId,需要参数token
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取用户信息，需要openId和oauth_consumer_key
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;
    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 通过构造方法，来获取openid,以及appId
     *
     * @param accessToken accessToken
     * @param appId       appId
     */
    public QQServiceImpl(String accessToken, String appId) {
        //把accessToken放进url中
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        //发起请求
        String result = getRestTemplate().getForObject(url, String.class);

        log.info(result);
        //System.out.println(result);

        //放进openId
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }


    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        //发起请求
        String result = getRestTemplate().getForObject(url, String.class);

        //
        log.info(result);

        QQUserInfo userInfo;
        //保存
        try {
            userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
