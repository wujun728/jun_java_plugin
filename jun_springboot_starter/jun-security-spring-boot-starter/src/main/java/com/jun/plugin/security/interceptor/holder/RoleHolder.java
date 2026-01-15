package com.jun.plugin.security.interceptor.holder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * 本地线程变量-缓存用户角色资源值信息
 *
 * @since 2023-06-04 13:58
 **/
public class RoleHolder {

    private final static ThreadLocal<Set<String>> roleSetLocal = new ThreadLocal<>();

    public static Set<String> getRoleSet() {
        Set<String> roleSet = roleSetLocal.get();
        if (Objects.isNull(roleSet)) {
            return new HashSet<String>();
        } else {
            return roleSet;
        }
    }

    public static void setRoleSet(Set<String> loginId) {
        roleSetLocal.set(loginId);
    }

    public static void clearRoleSet() {
        roleSetLocal.remove();
    }

}
