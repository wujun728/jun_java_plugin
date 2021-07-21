package org.wf.jwtp.util;

import org.springframework.web.method.HandlerMethod;
import org.wf.jwtp.annotation.Ignore;
import org.wf.jwtp.annotation.Logical;
import org.wf.jwtp.annotation.RequiresPermissions;
import org.wf.jwtp.annotation.RequiresRoles;
import org.wf.jwtp.perm.UrlPerm;
import org.wf.jwtp.perm.UrlPermResult;
import org.wf.jwtp.provider.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 权限检查工具类
 * Created by wangfan on 2018-12-27 下午 4:46.
 */
public class CheckPermissionUtil {
    private static final String TOKEN_TYPE = "Bearer";  // token类型
    private static final String TOKEN_HEADER_NAME = "Authorization";  // token在header中名称
    private static final String TOKEN_PARAM_NAME = "access_token";  // token在参数中名称

    /**
     * 检查是否忽略权限
     */
    public static boolean checkIgnore(Method method) {
        Ignore annotation = method.getAnnotation(Ignore.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) annotation = method.getDeclaringClass().getAnnotation(Ignore.class);
        return annotation != null;
    }

    /**
     * 检查权限是否符合
     */
    public static boolean checkPermission(Token token, HttpServletRequest request, HttpServletResponse response, Object handler, UrlPerm urlPerm) {
        Method method = ((HandlerMethod) handler).getMethod();
        RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) annotation = method.getDeclaringClass().getAnnotation(RequiresPermissions.class);
        String[] requiresPermissions;
        Logical logical;
        if (annotation != null) {
            requiresPermissions = annotation.value();
            logical = annotation.logical();
        } else if (urlPerm != null) {
            UrlPermResult upr = urlPerm.getPermission(request, response, (HandlerMethod) handler);
            requiresPermissions = upr.getValues();
            logical = upr.getLogical();
        } else {
            return true;
        }
        return SubjectUtil.hasPermission(token, requiresPermissions, logical);
    }

    /**
     * 检查角色是否符合
     */
    public static boolean checkRole(Token token, HttpServletRequest request, HttpServletResponse response, Object handler, UrlPerm urlPerm) {
        Method method = ((HandlerMethod) handler).getMethod();
        RequiresRoles annotation = method.getAnnotation(RequiresRoles.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) annotation = method.getDeclaringClass().getAnnotation(RequiresRoles.class);
        String[] requiresRoles;
        Logical logical;
        if (annotation != null) {
            requiresRoles = annotation.value();
            logical = annotation.logical();
        } else if (urlPerm != null) {
            UrlPermResult upr = urlPerm.getRoles(request, response, (HandlerMethod) handler);
            requiresRoles = upr.getValues();
            logical = upr.getLogical();
        } else {
            return true;
        }
        return SubjectUtil.hasRole(token, requiresRoles, logical);
    }

    /**
     * 检查是否是没有权限或没有角色
     */
    public static boolean isNoPermission(Token token, HttpServletRequest request, HttpServletResponse response, Object handler, UrlPerm urlPerm) {
        return !checkPermission(token, request, response, handler, urlPerm) || !checkRole(token, request, response, handler, urlPerm);
    }

    /**
     * 放行options请求
     */
    public static void passOptions(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
    }

    /**
     * 从request中取出前端传递的token
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String takeToken(HttpServletRequest request) {
        String access_token = request.getParameter(TOKEN_PARAM_NAME);
        if (access_token == null || access_token.trim().isEmpty()) {
            access_token = request.getHeader(TOKEN_HEADER_NAME);
            if (access_token != null && access_token.startsWith(TOKEN_TYPE)) {
                access_token = access_token.substring(TOKEN_TYPE.length() + 1);
            }
        }
        return access_token;
    }

}
