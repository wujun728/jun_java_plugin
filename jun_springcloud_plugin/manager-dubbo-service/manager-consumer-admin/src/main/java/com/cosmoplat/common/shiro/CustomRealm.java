package com.cosmoplat.common.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.common.utils.SpringContextUtils;
import com.cosmoplat.service.sys.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 授权
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Value("${spring.redis.key.prefix.userToken}")
    private String userTokenPrefix;

    /**
     * 执行授权逻辑
     */
    @Override
    @SuppressWarnings("unchecked")
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        RedisService redisService = (RedisService) SpringContextUtils.getBean("redisService");
        String sessionInfoStr = redisService.get(userTokenPrefix + principalCollection.getPrimaryPrincipal());
        if (StringUtils.isEmpty(sessionInfoStr)) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        JSONObject redisSession = JSON.parseObject(sessionInfoStr);
        if (redisSession == null) {
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }
        if (redisSession.get(Constant.ROLES_KEY) != null) {
            authorizationInfo.addRoles((Collection<String>) redisSession.get(Constant.ROLES_KEY));
        }
        if (redisSession.get(Constant.PERMISSIONS_KEY) != null) {
            authorizationInfo.addStringPermissions((Collection<String>) redisSession.get(Constant.PERMISSIONS_KEY));
        }
        return authorizationInfo;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getPrincipal(), getName());
    }
}
