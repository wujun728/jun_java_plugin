package com.lhb.lhbauth.jwt.demo.authentication.mobile;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Wujun
 * @description
 * @date 2019/1/2 0002 10:45
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg) {
        super("校验失败");
    }
}
