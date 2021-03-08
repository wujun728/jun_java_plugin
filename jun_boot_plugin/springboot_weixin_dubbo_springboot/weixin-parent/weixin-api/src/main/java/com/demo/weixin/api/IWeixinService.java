package com.demo.weixin.api;

import com.demo.weixin.entity.WeixinUser;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.enums.ProjectType;
import com.demo.weixin.enums.WebchatAuthScope;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

/**
 * @author Wujun
 * @description 微信用户service
 * @date 2017/7/27
 * @since 1.0
 */
public interface IWeixinService {

    /**
     * 获取微信授权地址
     *
     * @param projectType
     * @param clientType
     * @param webchatAuthScope
     * @return
     */
    String getAuthUrl(ProjectType projectType, ClientType clientType, String fromUrl, WebchatAuthScope webchatAuthScope) throws UnsupportedEncodingException, WeixinException;


    /**
     * 获取公众号的 accessToken
     *
     * @param appId
     * @param appSecret
     * @return
     */
    String getAccessToken(String appId, String appSecret) throws WeixinException, IOException, URISyntaxException;

    /**
     * 获取 jsApi 的ticket
     *
     * @param appId
     * @param appSecret
     * @return
     */
    String getJsApiTicket(String appId, String appSecret) throws IOException, URISyntaxException, WeixinException;


    /**
     * 授权成功后根据 授权码code等 获取用户unionId
     *
     * @param projectType
     * @param clientType
     * @param code
     * @return
     */
    String getWeixinUserUnionId(ProjectType projectType, ClientType clientType, String code) throws IOException, URISyntaxException, WeixinException;


    WeixinUser getWeixinUserByUnionId(String unionId);
}
