package com.spring;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springboot.Application;
import cn.springboot.config.shiro.vo.PermissionEnumUtil;
import cn.springboot.config.shiro.vo.RoleEnumUtil;
import cn.springboot.model.auth.Permission;
import cn.springboot.model.auth.Role;
import cn.springboot.model.auth.User;
import cn.springboot.service.auth.PermissionService;
import cn.springboot.service.auth.RoleService;
import cn.springboot.service.auth.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class InitServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 创建角色
     */
    private void addRoles() {
        try {
            roleService.addRole(RoleEnumUtil.超级管理员.getRole());
            roleService.addRole(RoleEnumUtil.普通用户.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建菜单
     */
    private void addPermission() {
        try {
            List<Permission> permis = PermissionEnumUtil.getPermissions();
            for (Permission permission : permis) {
                permissionService.addPermission(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建用户
     */
    private void addUsers() {
        try {
            Role adminRole = roleService.findRoleByCode(RoleEnumUtil.超级管理员.getRoleCode());
            Role commonRole = roleService.findRoleByCode(RoleEnumUtil.普通用户.getRoleCode());

            String password = "123456";

            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@163.com");
            admin.setTrueName("管理员");
            admin.setPassword(password);
            admin.setOrganizeId(adminRole.getId());
            userService.addUser(admin, adminRole);

            User wangxin = new User();
            wangxin.setUsername("wangxin");
            wangxin.setTrueName("王鑫");
            wangxin.setEmail("wangxin@139.com");
            wangxin.setPassword(password);
            wangxin.setOrganizeId(commonRole.getId());
            userService.addUser(wangxin, commonRole);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 给角色授权
    private void bindRolePermission() {
        try {
            Role adminRole = roleService.findRoleByCode(RoleEnumUtil.超级管理员.getRoleCode());
            Role commonRole = roleService.findRoleByCode(RoleEnumUtil.普通用户.getRoleCode());

            List<Permission> permis = PermissionEnumUtil.getPermissions();
            for (Permission permission : permis) {
                roleService.addRolePermission(adminRole.getCode(), permission.getKey());
                roleService.addRolePermission(commonRole.getCode(), permission.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInit() {
        try {
            addRoles();
            addPermission();
            addUsers();
            bindRolePermission();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
