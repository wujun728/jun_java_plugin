package org.wf.jwtp.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.jwtp.annotation.Ignore;
import org.wf.jwtp.client.AuthResult;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import org.wf.jwtp.util.TokenUtil;

/**
 * 统一认证中心
 * Created by wangfan on 2018-12-27 下午 4:46.
 */
@Ignore
@Controller
public class AuthCenterController {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private TokenStore tokenStore;

    @ResponseBody
    @GetMapping("/authentication")
    public AuthResult authentication(String access_token) {
        AuthResult authResult = new AuthResult();
        if (access_token == null || access_token.trim().isEmpty()) {
            authResult.setCode(AuthResult.CODE_ERROR);
            return authResult;
        }
        try {
            String tokenKey = tokenStore.getTokenKey();
            logger.debug("JwtPermission: ACCESS_TOKEN[" + access_token + "]   TOKEN_KEY[" + tokenKey + "]");
            String userId = TokenUtil.parseToken(access_token, tokenKey);
            // 检查token是否存在系统中
            Token token = tokenStore.findToken(userId, access_token);
            if (token == null) {
                logger.debug("JwtPermission: Token Not Found");
                authResult.setCode(AuthResult.CODE_ERROR);
                return authResult;
            }
            // 查询用户的角色和权限
            token.setRoles(tokenStore.findRolesByUserId(userId, token));
            token.setPermissions(tokenStore.findPermissionsByUserId(userId, token));
            authResult.setCode(AuthResult.CODE_OK);
            authResult.setToken(token);
        } catch (ExpiredJwtException e) {
            logger.debug("JwtPermission", e.getCause());
            authResult.setCode(AuthResult.CODE_EXPIRED);
        } catch (Exception e) {
            logger.debug("JwtPermission", e.getCause());
            authResult.setCode(AuthResult.CODE_ERROR);
        }
        return authResult;
    }

}
