package com.jun.plugin.security.exception;

import com.jun.plugin.security.consts.AuthConsts;

/**
 * 未登录或会话已失效-异常
 */
public class UnAuthorizedException extends AuthException {
    private static final long serialVersionUID = 8109117719383003891L;

    public UnAuthorizedException() {
        super(AuthConsts.CODE_UNAUTHORIZED, "未登录或会话已失效！");
    }
}
