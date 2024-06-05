package com.jun.plugin.common.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.jun.plugin.common.model.SysUser;

/**
 * 登录用户holder
 *
 * @date 2022/6/26
 */
public class LoginUserContextHolder {
    private static final ThreadLocal<SysUser> CONTEXT = new TransmittableThreadLocal<>();

    public static void setUser(SysUser user) {
        CONTEXT.set(user);
    }

    public static SysUser getUser() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}