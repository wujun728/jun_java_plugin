package com.kind.springboot.common.manager.impl;

import com.kind.springboot.common.config.UserConstants;
import com.kind.springboot.common.manager.TokenManager;
import com.kind.springboot.common.token.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/***
 * Function:Redis存储和验证token的实现类.<br/>
 * Date:     2016年8月11日 下午12:55:31 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Component
@SuppressWarnings("unchecked")
public class RedisTokenManager implements TokenManager {

    private RedisTemplate<Long, String> redis;


    @SuppressWarnings("rawtypes")
    @Autowired
    public void setRedis(RedisTemplate redis) {
        this.redis = redis;
        /**
         * 泛型设置成Long后必须更改对应的序列化方案
         */
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    public TokenDto createToken(long userId) {
        /**
         * 使用UUID作为源token
         */
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenDto model = new TokenDto(userId, token);
        /**
         * 存储到Redis并设置过期时间
         */
        redis.boundValueOps(userId).set(token, UserConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    public TokenDto getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        /**
         * 使用userId和源token简单拼接成的token，可以增加加密措施
         */
        long userId = Long.parseLong(param[0]);
        String token = param[1];
        return new TokenDto(userId, token);
    }

    public boolean checkToken(TokenDto model) {
        if (model == null) {
            return false;
        }
        String token = redis.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        /**
         * 如果验证成功，延长token的过期时间
         */
        redis.boundValueOps(model.getUserId()).expire(UserConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }

    public void removeToken(long userId) {
        redis.delete(userId);
    }
}
