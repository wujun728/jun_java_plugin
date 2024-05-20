package com.jun.plugin.security.interceptor.holder;

import java.util.Objects;

/**
 * 本地线程变量-缓存用户会话信息
 *
 * @version 2022-06-14 13:58
 **/
public class AuthenticeHolder {
    private final static ThreadLocal<Object> authentice = new ThreadLocal<>();

    public static Object getLoginId() {
        Object loginId = authentice.get();
        if (Objects.isNull(loginId)) {
            return null;
        } else {
            return loginId;
        }
    }

    public static void setLoginId(Object loginId) {
        authentice.set(loginId);
    }

    public static void clearLoginId() {
        authentice.remove();
    }
}
