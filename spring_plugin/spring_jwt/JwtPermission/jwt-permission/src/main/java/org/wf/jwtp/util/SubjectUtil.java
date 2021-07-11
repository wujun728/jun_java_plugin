package org.wf.jwtp.util;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 权限检查工具类
 * Created by wangfan on 2018-1-23 上午9:58:40
 */
public class SubjectUtil {
    public static final String REQUEST_TOKEN_NAME = "JWTP_TOKEN";  // request中存储token的name

    /**
     * 检查是否有指定角色
     *
     * @param token   Token
     * @param roles   角色
     * @param logical 逻辑
     * @return boolean
     */
    public static boolean hasRole(Token token, String[] roles, Logical logical) {
        if (token == null) return false;
        if (roles == null || roles.length <= 0) return true;
        boolean rs = false;
        for (String role : roles) {
            rs = contains(token.getRoles(), role);
            if (logical == (rs ? Logical.OR : Logical.AND)) {
                break;
            }
        }
        return rs;
    }

    /**
     * 检查是否有指定角色
     *
     * @param token Token
     * @param roles 角色
     * @return boolean
     */
    public static boolean hasRole(Token token, String roles) {
        return hasRole(token, new String[]{roles}, Logical.OR);
    }

    /**
     * 检查是否有指定角色
     *
     * @param request HttpServletRequest
     * @param roles   角色
     * @param logical 逻辑
     * @return boolean
     */
    public static boolean hasRole(HttpServletRequest request, String[] roles, Logical logical) {
        return hasRole(getToken(request), roles, logical);
    }

    /**
     * 检查是否有指定角色
     *
     * @param request HttpServletRequest
     * @param roles   角色
     * @return boolean
     */
    public static boolean hasRole(HttpServletRequest request, String roles) {
        return hasRole(getToken(request), new String[]{roles}, Logical.OR);
    }

    /**
     * 检查是否有指定权限
     *
     * @param token       Token
     * @param permissions 权限
     * @param logical     逻辑
     * @return boolean
     */
    public static boolean hasPermission(Token token, String[] permissions, Logical logical) {
        if (token == null) return false;
        if (permissions == null || permissions.length <= 0) return true;
        boolean rs = false;
        for (String perm : permissions) {
            rs = contains(token.getPermissions(), perm);
            if (logical == (rs ? Logical.OR : Logical.AND)) {
                break;
            }
        }
        return rs;
    }

    /**
     * 检查是否有指定权限
     *
     * @param token       Token
     * @param permissions 权限
     * @return boolean
     */
    public static boolean hasPermission(Token token, String permissions) {
        return hasPermission(token, new String[]{permissions}, Logical.OR);
    }

    /**
     * 检查是否有指定权限
     *
     * @param request     HttpServletRequest
     * @param permissions 权限
     * @param logical     逻辑
     * @return boolean
     */
    public static boolean hasPermission(HttpServletRequest request, String[] permissions, Logical logical) {
        return hasPermission(getToken(request), permissions, logical);
    }

    /**
     * 检查是否有指定权限
     *
     * @param request     HttpServletRequest
     * @param permissions 权限
     * @return boolean
     */
    public static boolean hasPermission(HttpServletRequest request, String permissions) {
        return hasPermission(getToken(request), new String[]{permissions}, Logical.OR);
    }

    /**
     * 从request中获取token
     *
     * @param request HttpServletRequest
     * @return Token
     */
    public static Token getToken(HttpServletRequest request) {
        Object token = request.getAttribute(REQUEST_TOKEN_NAME);
        return token == null ? null : (Token) token;
    }

    /**
     * 解析token
     *
     * @param request HttpServletRequest
     * @return Token
     */
    public static Token parseToken(HttpServletRequest request) {
        return parseToken(request, getBean(TokenStore.class));
    }

    /**
     * 解析token
     *
     * @param request    HttpServletRequest
     * @param tokenStore TokenStore
     * @return Token
     */
    public static Token parseToken(HttpServletRequest request, TokenStore tokenStore) {
        Token token = getToken(request);
        if (token == null && tokenStore != null) {
            // 获取token
            String access_token = CheckPermissionUtil.takeToken(request);
            if (access_token != null && !access_token.trim().isEmpty()) {
                try {
                    String tokenKey = tokenStore.getTokenKey();
                    String userId = TokenUtil.parseToken(access_token, tokenKey);
                    token = tokenStore.findToken(userId, access_token);
                    if (token != null) {  // 查询用户的角色和权限
                        token.setRoles(tokenStore.findRolesByUserId(userId, token));
                        token.setPermissions(tokenStore.findPermissionsByUserId(userId, token));
                    }
                } catch (ExpiredJwtException e) {
                    System.out.println("token已过期");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return token;
    }


    /**
     * 判断数组是否包含指定元素
     *
     * @param array 数组
     * @param str   元素
     * @return boolean
     */
    private static boolean contains(String[] array, String str) {
        if (array == null || array.length == 0) return false;
        for (String item : array) {
            if (item.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            if (applicationContext == null) return null;
            ServletContext servletContext = applicationContext.getServletContext();
            if (servletContext == null) return null;
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            if (context == null) return null;
            Collection<T> beans = context.getBeansOfType(clazz).values();
            while (beans.iterator().hasNext()) {
                T bean = beans.iterator().next();
                if (bean != null) return bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
