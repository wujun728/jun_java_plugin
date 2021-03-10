package com.demo.weixin.service.impl;

import com.demo.weixin.api.IQQService;
import com.demo.weixin.api.ICacheService;
import com.demo.weixin.clients.IQQClient;
import com.demo.weixin.entity.QQUser;
import com.demo.weixin.exception.WeixinException;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.qq.connect.utils.RandomStatusGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @description QQ服务实现
 * @date 2017/7/25
 * @since 1.0
 */
public class QQServiceImpl implements IQQService {

    // qq用户信息redis的key
    public static final String QQ_USER_PREFIX = "qq_user_%s";

    @Value("${qq_user_expire_minutes}")
    private int qqUserExpireMinutes;

    @Resource
    private RedisTemplate<String, QQUser> redisTemplate;

    @Resource
    private IQQClient qqClient;

    @Resource
    private ICacheService cacheService;

    @Override
    public String getAuthUrl(String from) throws QQConnectException, WeixinException {
        if (StringUtils.isBlank(from)) {
            throw new WeixinException(-1, "from参数为空");
        }
        // 获取唯一的state
        String state = this.getUniqueState();
        // 保存qq授权成功后的回调地址
        cacheService.saveAuthCallBackUrl(from, state);
        return new Oauth().getAuthorizeURL(state);
    }

    @Override
    public String getUniqueState() {
        return RandomStatusGenerator.getUniqueState();
    }

    @Override
    public String getQQUserOpenId(String queryString, String state) throws QQConnectException {

        AccessToken accessToken = new Oauth().getAccessTokenByQueryString(queryString, state);
        // 利用获取到的accessToken 去获取当前用户的openid -------- start
        OpenID openIdObj = new OpenID(accessToken.getAccessToken());
        String openId = openIdObj.getUserOpenID();

        // 获取相应的用户信息
        QQUser user = qqClient.getQQUser(accessToken.getAccessToken(), openId);
        if (user == null) {
            return null;
        }
        // 保存用户信息
        this.saveQQUser(user);
        return openId;
    }

    /**
     * 保存QQ用户信息到redis
     *
     * @param qqUser
     */
    private void saveQQUser(QQUser qqUser) {
        if (qqUser == null || StringUtils.isBlank(qqUser.getOpenId())) {
            return;
        }

        String qqUserKey = String.format(QQ_USER_PREFIX, qqUser.getOpenId());
        redisTemplate.delete(qqUserKey);
        redisTemplate.opsForValue().set(qqUserKey, qqUser);
        // 设置失效时间
        redisTemplate.expire(qqUserKey, qqUserExpireMinutes, TimeUnit.MINUTES);
    }

    @Override
    public QQUser getQQUserByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        String qqUserKey = String.format(QQ_USER_PREFIX, openId);
        return redisTemplate.opsForValue().get(qqUserKey);
    }
}
