package com.jun.plugin.security.util;

import com.jun.plugin.security.annotation.Ignore;
import com.jun.plugin.security.annotation.RequiresPermissions;
import com.jun.plugin.security.annotation.RequiresRoles;
import com.jun.plugin.security.enums.Logical;
import com.jun.plugin.security.interceptor.holder.AuthenticeHolder;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.util.StringUtils;
import com.jun.plugin.security.interceptor.holder.PermissionHolder;
import com.jun.plugin.security.interceptor.holder.RoleHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AuthUtil {

    /**
     * 获取当前请求对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前响应对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        HttpServletResponse response = null;
        try {
            response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
            return response;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取用户token
     *
     * @return token
     */
    public static String getToken(String tokenName) {
        HttpServletRequest request = null;
        try {
            request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
            String token = request.getHeader(tokenName);
            if (StringUtils.isEmpty(token)) {
                token = CookieUtil.getCookie(request, tokenName);
            }
            // cookie里取不到，再从请求参数里面取
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(tokenName);
            }
            return token;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 获取用户token
     *
     * @return token
     */
    public static String getToken(HttpServletRequest request, String tokenName) {
        // 从请求中获取token，先从Header里取，取不到的话再从cookie里取（适配前后端分离的模式）
        String token = request.getHeader(tokenName);
        if (StringUtils.isEmpty(token)) {
            token = CookieUtil.getCookie(request, tokenName);
        }
        // cookie里取不到，再从请求参数里面取
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(tokenName);
        }
        return token;
    }


    /**
     * 检查Method上是否有@Ignore注解，决定是否忽略会话认证
     *
     * @param method Method
     * @return true or false
     */
    public static boolean checkIgnore(Method method) {
        // 先获取方法上的注解
        Ignore annotation = method.getAnnotation(Ignore.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(Ignore.class);
        }
        return annotation != null;
    }


    /**
     * 检查Method上是否有@RequiresPermissions注解，并检验其值，通过返回true，拒绝返回false
     *
     * @param method        Method
     * @param permissionSet 权限列表
     * @return true or false
     */
    public static boolean checkPermission(Method method, Set<String> permissionSet) {
        // 当permissionSet为空时，赋初值，防止空指针错误
        if (permissionSet == null) {
            permissionSet = new HashSet<>();
        }

        RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(RequiresPermissions.class);
        }
        // 接口上没有注解，说明这个接口无权限控制，直接通过
        if (annotation == null) {
            return true;
        }

        String[] permissions = annotation.value();
        Logical logical = annotation.logical();
        if (logical == Logical.OR) {
            // 如果有任何一个权限，返回true，否则返回false（拥有其一）
            for (String perm : permissions) {
                if (permissionSet.contains(perm)) {
                    return true;
                }
            }
            return false;
        } else if (logical == Logical.AND) {
            // 只要有一个权限不是true的，就返回false（同时拥有）
            for (String perm : permissions) {
                if (!permissionSet.contains(perm)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查Method上是否有@RequiresPermissions注解，并检验其值，通过返回true，拒绝返回false
     *
     * @param method  Method
     * @param roleSet 角色列表
     * @return true or false
     */
    public static boolean checkRole(Method method, Set<String> roleSet) {
        // 当roleSet为空时，赋初值，防止空指针错误
        if (roleSet == null) {
            roleSet = new HashSet<>();
        }

        RequiresRoles annotation = method.getAnnotation(RequiresRoles.class);
        // 方法上没有注解再检查类上面有没有注解
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(RequiresRoles.class);
        }
        // 接口上没有注解，说明这个接口无权限控制，直接通过
        if (annotation == null) {
            return true;
        }

        String[] roles = annotation.value();
        Logical logical = annotation.logical();

        if (logical == Logical.OR) {
            // 如果有任何一个角色，返回true，否则返回false（拥有其一）
            for (String ro : roles) {
                if (roleSet.contains(ro)) {
                    return true;
                }
            }
            return false;
        } else if (logical == Logical.AND) {
            // 只要有一个角色不是true的，就返回false（同时拥有）
            for (String ro : roles) {
                if (!roleSet.contains(ro)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取当前登录用户的LoginId
     *
     * @return Object
     */
    public static Object getLoginId() {
        return AuthenticeHolder.getLoginId();
    }

    /**
     * 判断拥有角色：role1
     *
     * @return
     */
    public static boolean hasRole(String role) {
        Set<String> roleSet = RoleHolder.getRoleSet();
        if (roleSet == null || roleSet.isEmpty()) {
            return false;
        }
        return roleSet.contains(role);
    }

    /**
     * 判断拥有角色：role1 and role2
     *
     * @param roles
     * @return
     */
    public static boolean hasAllRole(String... roles) {
        Set<String> roleSet = RoleHolder.getRoleSet();
        if (roleSet == null || roleSet.isEmpty()) {
            return false;
        }
        // 只要有一个角色不是true的，就返回false（同时拥有）
        for (String ro : roles) {
            if (!roleSet.contains(ro)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断拥有角色：role1 or role2 or role3 有其一即可
     *
     * @param roles
     * @return
     */
    public static boolean hasAnyRole(String... roles) {
        Set<String> roleSet = RoleHolder.getRoleSet();
        if (roleSet == null || roleSet.isEmpty()) {
            return false;
        }
        // 如果有任何一个角色，返回true，否则返回false（拥有其一）
        for (String ro : roles) {
            if (roleSet.contains(ro)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断拥有权限：permission1
     *
     * @return
     */
    public static boolean hasPermission(String permission) {
        Set<String> permissionSet = PermissionHolder.getPermissionSet();
        if (permissionSet == null || permissionSet.isEmpty()) {
            return false;
        }
        return permissionSet.contains(permission);
    }

    /**
     * 判断拥有角色：permission1 and permission2
     *
     * @param permissions
     * @return
     */
    public static boolean hasAllPermission(String... permissions) {
        Set<String> permissionSet = PermissionHolder.getPermissionSet();
        if (permissionSet == null || permissionSet.isEmpty()) {
            return false;
        }
        // 只要有一个权限不是true的，就返回false（同时拥有）
        for (String pe : permissions) {
            if (!permissionSet.contains(pe)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断拥有权限：permission1 or permission2 or permission3 有其一即可
     *
     * @param permissions
     * @return
     */
    public static boolean hasAnyPermission(String... permissions) {
        Set<String> permissionSet = PermissionHolder.getPermissionSet();
        if (permissionSet == null || permissionSet.isEmpty()) {
            return false;
        }
        // 如果有任何一个权限，返回true，否则返回false（拥有其一）
        for (String pe : permissions) {
            if (permissionSet.contains(pe)) {
                return true;
            }
        }
        return false;
    }
}
