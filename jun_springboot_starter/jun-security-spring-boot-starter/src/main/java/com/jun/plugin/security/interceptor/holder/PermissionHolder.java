package com.jun.plugin.security.interceptor.holder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 本地线程变量-缓存用户权限资源值信息
 *
 * @since 2023-06-04 13:58
 **/
public class PermissionHolder {
    private final static ThreadLocal<Set<String>> permissionSetLocal = new ThreadLocal<>();

    public static Set<String> getPermissionSet() {
        Set<String> permissionSet = permissionSetLocal.get();
        if (Objects.isNull(permissionSet)) {
            return new HashSet<String>();
        } else {
            return permissionSet;
        }
    }

    public static void setPermissionSet(Set<String> loginId) {
        permissionSetLocal.set(loginId);
    }

    public static void clearPermissionSet() {
        permissionSetLocal.remove();
    }
}
