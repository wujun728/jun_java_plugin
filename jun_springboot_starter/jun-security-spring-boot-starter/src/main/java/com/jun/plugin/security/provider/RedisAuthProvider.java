package com.jun.plugin.security.provider;


import org.springframework.util.Assert;
import com.jun.plugin.security.AuthProperties;
import com.jun.plugin.security.consts.AuthConsts;
import com.jun.plugin.security.util.TokenGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 操作token和会话的接口（通过redis实现）
 *
 * @version 2023-01-06-9:33
 **/
public class RedisAuthProvider extends AbstractAuthProvider implements AuthProvider {
    final static Logger log = LoggerFactory.getLogger(RedisAuthProvider.class);

    private final StringRedisTemplate redisTemplate;
    private final AuthProperties authProperties;

    public RedisAuthProvider(StringRedisTemplate redisTemplate, AuthProperties authProperties) {
        this.redisTemplate = redisTemplate;
        this.authProperties = authProperties;
    }

    @Override
    protected AuthProperties getAuthProperties() {
        return this.authProperties;
    }

    /**
     * 刷新token
     *
     * @param token 令牌
     * @return true成功，false失败
     */
    @Override
    public boolean refreshToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            return redisTemplate.expire(AuthConsts.AUTH_TOKEN_KEY + token, authProperties.getTimeout(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("RedisAuthProvider refreshToken failed, Exception：{e}", e);
            return false;
        }
    }

    /**
     * 检查token是否失效
     *
     * @param token 令牌
     * @return true未失效，false已失效
     */
    @Override
    public boolean checkToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            return redisTemplate.hasKey(AuthConsts.AUTH_TOKEN_KEY + token);
        } catch (Exception e) {
            log.error("RedisAuthProvider checkToken failed, Exception：{e}", e);
            return false;
        }
    }


    /**
     * 创建一个新的token
     *
     * @param loginId 会话登录：参数填写要登录的账号id，建议的数据类型：long | int | String， 不可以传入复杂类型，如：User、Admin 等等
     * @return token令牌
     */
    @Override
    public String createToken(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null!");
        try {
            String token = TokenGenUtil.genTokenStr(getAuthProperties().getTokenStyle());
            redisTemplate.opsForValue().set(AuthConsts.AUTH_TOKEN_KEY + token, String.valueOf(loginId), getAuthProperties().getTimeout(), TimeUnit.SECONDS);
            return token;
        } catch (Exception e) {
            log.error("RedisAuthProvider createToken failed, Exception：{e}", e);
            return null;
        }
    }


    /**
     * 根据token，获取loginId
     *
     * @param token 令牌
     * @return loginId
     */
    @Override
    public Object getLoginId(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            return redisTemplate.opsForValue().get(AuthConsts.AUTH_TOKEN_KEY + token);
        } catch (Exception e) {
            log.error("RedisAuthProvider getLoginId failed, Exception：{e}", e);
            return null;
        }
    }

    /**
     * 删除token
     *
     * @param token 令牌
     * @return true成功，false失败
     */
    @Override
    public boolean deleteToken(String token) {
        Assert.hasText(token, "The token cannot be empty!");
        try {
            return redisTemplate.delete(AuthConsts.AUTH_TOKEN_KEY + token);
        } catch (Exception e) {
            log.error("RedisAuthProvider deleteToken failed, Exception：{e}", e);
            return false;
        }
    }

    /**
     * 通过loginId删除token（目前是通过keys命令模糊查询的，数据量特别大时会有性能问题，后续优化）
     *
     * @param loginId 身份唯一值
     * @return true成功，false失败
     */
    @Override
    public boolean deleteTokenByLoginId(Object loginId) {
        Assert.notNull(loginId, "The loginId cannot be null!");
        try {
            Set<String> keys = redisTemplate.keys(AuthConsts.AUTH_TOKEN_KEY.concat("*"));
            if (Objects.nonNull(keys) && !keys.isEmpty()) {
                for (String key : keys) {
                    String loginIdInRedis = redisTemplate.opsForValue().get(key);
                    if (!StringUtils.isEmpty(loginIdInRedis) && loginIdInRedis.equals(loginId)) {
                        redisTemplate.delete(key);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error("RedisAuthProvider deleteToken failed, Exception：{e}", e);
            return false;
        }
    }

}
