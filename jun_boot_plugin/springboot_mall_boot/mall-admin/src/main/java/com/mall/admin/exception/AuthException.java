package com.mall.admin.exception;


/**
 * torkn认证异常
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/25 13:30
 */
public class AuthException extends RuntimeException {

    public AuthException() {
        super("用户未认证");
    }
}
