package com.lzb.onlinejava.complier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.security.Permission;

/**
 * author: haiyangp
 * date:  2017/9/26
 * desc: APP入口
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.lzb.onlinejava.complier.filter")
public class ComplierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplierApplication.class, args);
        initPermission();
    }

    /**
     * 配置权限，禁止执行CMD 命令
     */
    private static void initPermission() {
        SecurityManager originalSecurityManager = System.getSecurityManager();
        if (originalSecurityManager == null) {
            // 创建自己的SecurityManager
            SecurityManager sm = new SecurityManager() {
                private void check(Permission perm) {
//                    // 禁止exec
//                    if (perm instanceof java.io.FilePermission) {
//                        String actions = perm.getActions();
//                        if (actions != null && actions.contains("execute")) {
//                            throw new SecurityException("execute denied!");
//                        }
//                    }
//
//                    // 禁止设置新的SecurityManager，保护自己
//                    if (perm instanceof java.lang.RuntimePermission) {
//                        String name = perm.getName();
//                        if (name != null && name.contains("setSecurityManager")) {
//                            throw new SecurityException("System.setSecurityManager denied!");
//                        }
//                    }


                }

                @Override
                public void checkPermission(Permission perm) {
                    check(perm);
                }

                @Override
                public void checkPermission(Permission perm, Object context) {
                    check(perm);
                }
            };

            System.setSecurityManager(sm);
        }
    }
}
