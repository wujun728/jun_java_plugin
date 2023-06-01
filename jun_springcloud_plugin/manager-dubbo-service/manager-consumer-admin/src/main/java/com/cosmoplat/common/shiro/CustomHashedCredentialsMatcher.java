package com.cosmoplat.common.shiro;

import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.SpringContextUtils;
import com.cosmoplat.service.sys.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Value;

/**
 * 认证
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        RedisService redisDb = (RedisService) SpringContextUtils.getBean("redisService");

        String accessToken = (String) token.getPrincipal();
        if (!redisDb.exists(userTokenPrefix + accessToken)) {
            SecurityUtils.getSubject().logout();
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        return true;
    }
}
