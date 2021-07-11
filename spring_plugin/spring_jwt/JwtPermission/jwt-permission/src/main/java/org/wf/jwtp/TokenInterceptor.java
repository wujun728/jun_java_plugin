package org.wf.jwtp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.wf.jwtp.exception.ErrorTokenException;
import org.wf.jwtp.exception.ExpiredTokenException;
import org.wf.jwtp.exception.TokenWillExpireHandler;
import org.wf.jwtp.exception.UnauthorizedException;
import org.wf.jwtp.perm.UrlPerm;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import org.wf.jwtp.util.CheckPermissionUtil;
import org.wf.jwtp.util.SubjectUtil;
import org.wf.jwtp.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 拦截器
 * Created by wangfan on 2018-12-27 下午 4:46.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final Log logger = LogFactory.getLog(this.getClass());
    /**
     * 操作token的接口
     */
    private TokenStore tokenStore;
    /**
     * 权限自动匹配模式
     */
    private UrlPerm urlPerm;
    /**
     * token过期自定义处理
     */
    private TokenWillExpireHandler tokenWillExpireHandler;

    public TokenInterceptor() {
    }

    public TokenInterceptor(TokenStore tokenStore) {
        setTokenStore(tokenStore);
    }

    public TokenInterceptor(TokenStore tokenStore, UrlPerm urlPerm) {
        setTokenStore(tokenStore);
        setUrlPerm(urlPerm);
    }

    public TokenInterceptor(TokenStore tokenStore, TokenWillExpireHandler tokenWillExpireHandler) {
        setTokenStore(tokenStore);
        setTokenWillExpireHandler(tokenWillExpireHandler);
    }

    public TokenInterceptor(TokenStore tokenStore, UrlPerm urlPerm, TokenWillExpireHandler tokenWillExpireHandler) {
        setTokenStore(tokenStore);
        setUrlPerm(urlPerm);
        setTokenWillExpireHandler(tokenWillExpireHandler);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行options请求
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            CheckPermissionUtil.passOptions(response);
            return false;
        }
        Method method = null;
        if (handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        }
        // 检查是否忽略权限验证
        if (method == null || CheckPermissionUtil.checkIgnore(method)) {
            return super.preHandle(request, response, handler);
        }
        // 获取token
        String access_token = CheckPermissionUtil.takeToken(request);
        if (access_token == null || access_token.trim().isEmpty()) {
            throw new ErrorTokenException("Token不能为空");
        }
        Claims claims;
        try {
            String tokenKey = tokenStore.getTokenKey();
            logger.debug("JwtPermission: ACCESS_TOKEN[" + access_token + "]   TOKEN_KEY[" + tokenKey + "]");
            claims = TokenUtil.parseToken(access_token, tokenKey);
        } catch (ExpiredJwtException e) {
            logger.debug("JwtPermission", e.getCause());
            throw new ExpiredTokenException();
        } catch (Exception e) {
            logger.debug("JwtPermission", e.getCause());
            throw new ErrorTokenException();
        }
        if (claims != null) {
            String userId = claims.getSubject();
            // 检查token是否存在系统中
            Token token = tokenStore.findToken(userId, access_token);
            if (token == null) {
                logger.debug("JwtPermission: Token Not Found");
                throw new ErrorTokenException();
            }
            token.setIssuedAt(claims.getIssuedAt());
            token.setExpireTime(claims.getExpiration());
            request.setAttribute(SubjectUtil.REQUEST_TOKEN_NAME, token);
            // token将要过期处理
            if (tokenWillExpireHandler != null && !tokenWillExpireHandler.handle(tokenStore, request, response)) {
                return false;
            }
            // 查询用户的角色和权限
            token.setRoles(tokenStore.findRolesByUserId(userId, token));
            token.setPermissions(tokenStore.findPermissionsByUserId(userId, token));
            // 检查权限
            if (CheckPermissionUtil.isNoPermission(token, request, response, handler, urlPerm)) {
                throw new UnauthorizedException();
            }
        }
        return super.preHandle(request, response, handler);
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public void setUrlPerm(UrlPerm urlPerm) {
        this.urlPerm = urlPerm;
    }

    public UrlPerm getUrlPerm() {
        return urlPerm;
    }

    public void setTokenWillExpireHandler(TokenWillExpireHandler tokenWillExpireHandler) {
        this.tokenWillExpireHandler = tokenWillExpireHandler;
    }

    public TokenWillExpireHandler getTokenWillExpireHandler() {
        return tokenWillExpireHandler;
    }

}
