package org.wf.jwtp.exception;

import io.jsonwebtoken.Claims;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import org.wf.jwtp.util.SubjectUtil;
import org.wf.jwtp.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 默认token过期异常处理
 * Created by wangfan on 2020-03-25 13:53
 */
public class DefaultTokenWillExpireHandler implements TokenWillExpireHandler {
    /**
     * refresh_token返回给前端的header名称
     */
    private String responseHeader = "Authorization";
    /**
     * 将要过期的时间, 单位分钟
     */
    private int willExpireTime = 30;

    public DefaultTokenWillExpireHandler() {
    }

    public DefaultTokenWillExpireHandler(String responseHeader) {
        setResponseHeader(responseHeader);
    }

    @Override
    public boolean handle(TokenStore tokenStore, HttpServletRequest request, HttpServletResponse response) {
        Token token = SubjectUtil.getToken(request);
        if (token == null) return true;
        if ((token.getExpireTime().getTime() - new Date().getTime()) / 1000 / 60 < willExpireTime) {
            long expire = (token.getExpireTime().getTime() - token.getIssuedAt().getTime()) / 1000;
            String key = tokenStore.getTokenKey();
            Token t = TokenUtil.buildToken(token.getUserId(), expire, null, TokenUtil.parseHexKey(key), false);
            t.setRefreshToken(token.getRefreshToken());
            t.setRefreshTokenExpireTime(token.getRefreshTokenExpireTime());
            t.setRoles(token.getRoles());
            t.setPermissions(token.getPermissions());
            response.setHeader(responseHeader, t.getAccessToken());
        }
        return true;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public int getWillExpireTime() {
        return willExpireTime;
    }

    public void setWillExpireTime(int willExpireTime) {
        this.willExpireTime = willExpireTime;
    }
}
