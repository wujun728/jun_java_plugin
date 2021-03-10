package com.demo.weixin.clients;

import com.demo.weixin.entity.*;
import com.demo.weixin.enums.WebchatAuthScope;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 微信公众号接口
 */
public interface IWeixinClient {

    /**
     * 微信公众号网页授权获取用户信息时，授权url
     * @param appId
     * @param redirectUri 回调地址，无需encode
     * @param scope
     * @param state
     * @return
     */
    String getWeixinWebchatAuthUrl(String appId, String redirectUri, WebchatAuthScope scope, String state);

    /**
     * 获取公众号token
     * @param appId
     * @param appSecret
     * @return 公众号token
     */
    WeixinAccessToken getAccessToken(String appId, String appSecret) throws URISyntaxException, IOException;

    /**
     * 获取微信jssdk临时票据
     * @param accessToken
     * @return
     */
    WeixinJsApiTicket getWeixinJsApiTicket(String accessToken) throws URISyntaxException, IOException;

    /**
     * 获取微信用户accessToken
     * @param appId
     * @param appSecret
     * @param code
     * @return
     * @throws URISyntaxException
     */
    UserAccessToken getUserAccessToken(String appId, String appSecret, String code) throws URISyntaxException, IOException;

    /**
     * 获取微信用户基本信息
     * @param accessToken
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    WeixinUser getWeixinUserInfo(UserAccessToken accessToken) throws URISyntaxException, IOException;


    byte[] downloadPhoto(String accessToken, String mediaId) throws URISyntaxException, IOException;

    /**
     * <p>微信公众号创建菜单</p>
     *
     * @param accessToken
     */
    BaseWeixinResponse createMenu(String accessToken, List<WeixinMenu> menuList) throws URISyntaxException, IOException;

}
