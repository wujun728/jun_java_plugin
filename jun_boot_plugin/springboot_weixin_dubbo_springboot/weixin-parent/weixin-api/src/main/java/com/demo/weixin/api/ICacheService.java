package com.demo.weixin.api;

/**
 * @author Wujun
 * @description
 * @date 2017/7/27
 * @since 1.0
 */
public interface ICacheService {

    /**
     * 缓存 accessToken
     *
     * @param accessToken
     * @param corpId/AppId
     * @param corpSecret/appSecret
     * @return
     */
    String saveAccessToken(String accessToken, String corpId, String corpSecret);

    /**
     * 从缓存中获取accessToken
     *
     * @param corpId/AppId
     * @param corpSecret/appSecret
     * @return
     */
    String getAccessToken(String corpId, String corpSecret);

    /**
     * 保存授权成功后的回调地址
     *
     * @param formUrl
     * @param state
     * @return
     */
    String saveAuthCallBackUrl( String formUrl, String state);

    /**
     * 获取授权成功后的回调地址
     *
     * @param state
     * @return
     */
    String getAuthCallBackUrl(String state);


    /**
     * 保存 jsApiTicket
     *
     * @param appId/corpId
     * @param jsApiTicket
     * @return
     */
    String saveJsApiTicket(String appId, String jsApiTicket);

    /**
     * 获取缓存的 jsApiTicekt
     *
     * @param appId / corpId
     * @return
     */
    String getJsApiTicket(String appId);

}
