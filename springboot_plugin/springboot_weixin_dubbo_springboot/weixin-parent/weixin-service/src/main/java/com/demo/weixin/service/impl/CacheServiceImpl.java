package com.demo.weixin.service.impl;

import com.demo.weixin.api.ICacheService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @description 缓存服务实现
 * @date 2017/7/27
 * @since 1.0
 */
public class CacheServiceImpl implements ICacheService {

    // 授权成功后的回调地址缓存 key
    String AUTH_CALL_BACK_PREFIX = "auth_call_back_%s";

    // access token 缓存的key
    public static final String ACCESS_TOKEN_PREFIX = "access_token_%s_%s";

    // jsApi ticket
    public static final String JSAPI_TICKET_PREFIX = "jsapi_ticket_%";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Value("${auth_callback_url_expire_days}")
    private int authCallBackUrlExpireDays;

    @Value("${access_token_expire_hours}")
    private int accessTokenExpireHours;

    @Value("${js_api_ticket_expire_hours}")
    private int jsApiTicketExpireHours;


    /**
     * 缓存 accessToken
     *
     * @param accessToken
     * @param corpId/AppId
     * @param corpSecret/appSecret
     * @return
     */
    @Override
    public String saveAccessToken(String accessToken, String corpId, String corpSecret) {
        if (StringUtils.isAnyBlank(accessToken, corpId, corpSecret)) {
            return null;
        }
        String accessTokenKey = String.format(ACCESS_TOKEN_PREFIX, corpId, corpSecret);
        // 先清空该用户的session
        redisTemplate.delete(accessTokenKey);
        redisTemplate.opsForValue().set(accessTokenKey, accessToken);
        // 设置失效时间
        redisTemplate.expire(accessTokenKey, accessTokenExpireHours, TimeUnit.HOURS);
        return accessToken;
    }

    /**
     * 从缓存中获取accessToken
     *
     * @param corpId/AppId
     * @param corpSecret/appSecret
     * @return
     */
    @Override
    public String getAccessToken(String corpId, String corpSecret) {
        if (StringUtils.isAnyBlank(corpId, corpSecret)) {
            return null;
        }
        String accessTokenKey = String.format(ACCESS_TOKEN_PREFIX, corpId, corpSecret);
        return redisTemplate.opsForValue().get(accessTokenKey);
    }

    /**
     * 保存授权成功后回调的URL
     *
     * @param formUrl
     * @param state
     * @return
     */
    public String saveAuthCallBackUrl(String formUrl, String state) {

        if (StringUtils.isAnyBlank(formUrl, state)) {
            return null;
        }

        String callBackUrlKey = String.format(AUTH_CALL_BACK_PREFIX, state);

        // 先清空该用户的session
        redisTemplate.delete(callBackUrlKey);
        redisTemplate.opsForValue().set(callBackUrlKey, formUrl);
        // 设置失效时间
        redisTemplate.expire(callBackUrlKey, authCallBackUrlExpireDays, TimeUnit.DAYS);
        return formUrl;
    }

    /**
     * 获取授权成功后的回调地址
     *
     * @param state
     * @return
     */
    public String getAuthCallBackUrl(String state) {
        String callBackUrlKey = String.format(AUTH_CALL_BACK_PREFIX, state);
        return redisTemplate.opsForValue().get(callBackUrlKey);
    }

    @Override
    public String saveJsApiTicket(String appId, String jsApiTicket) {
        if (StringUtils.isAnyBlank(appId, jsApiTicket)) {
            return null;
        }
        String jsApiTicketKey = String.format(JSAPI_TICKET_PREFIX, appId);
        // 先清空
        redisTemplate.delete(jsApiTicketKey);
        redisTemplate.opsForValue().set(jsApiTicketKey, jsApiTicket);
        // 设置失效时间
        redisTemplate.expire(jsApiTicketKey, jsApiTicketExpireHours, TimeUnit.HOURS);
        return jsApiTicket;
    }

    @Override
    public String getJsApiTicket(String appId) {
        String jsApiTicketKey = String.format(JSAPI_TICKET_PREFIX, appId);
        return redisTemplate.opsForValue().get(jsApiTicketKey);
    }
}
