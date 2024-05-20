package com.jun.plugin.security.consts;


/**
 * 系统常量类
 * @version 2023-01-06-9:33
 **/
public class AuthConsts {

    /**
     * 登录用户 令牌 Redis Key 前缀
     */
    public static final String AUTH_TOKEN_KEY = "jun-security:auth:token:";

    // 无权限访问
    public static int CODE_NO_PERMISSION = 403;

    // 未登录或会话已失效
    public static int CODE_UNAUTHORIZED = 401;
}
