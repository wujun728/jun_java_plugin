package cn.springmvc.jpa.service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.jpa.common.constants.Constants;
import cn.springmvc.jpa.entity.Permission;
import cn.springmvc.jpa.entity.Role;
import cn.springmvc.jpa.entity.User;
import cn.springmvc.jpa.web.util.MenuUtil;

/**
 * @author Wujun
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml" })
@ActiveProfiles("development")
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

            Role r1 = new Role();
            r1.setName(Constants.SYSTEM_ROLE_NAME);// 系统管理员
            r1.setCode(Constants.SYSTEM_ROLE_CODE);
            r1.setRemark(Constants.SYSTEM_ROLE_NAME);
            roleService.addRole(r1);

            Role r2 = new Role();
            r2.setName(Constants.COMMON_ROLE_NAME);// 普通用户
            r2.setCode(Constants.COMMON_ROLE_CODE);
            r2.setRemark(Constants.COMMON_ROLE_NAME);
            roleService.addRole(r2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建菜单
     */
    private void addPermission() {
        try {
            Collection<Permission> permis = MenuUtil.importPermissionData();
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
            Role systemRole = roleService.findRoleByCode(Constants.SYSTEM_ROLE_CODE);// 管理员
            Role commonRole = roleService.findRoleByCode(Constants.COMMON_ROLE_CODE);// 普通用户
            String initPassword = "123456";

            User wangxin = new User();
            wangxin.setName("admin");
            wangxin.setEmail("infowangxin@163.com");
            wangxin.setTrueName("管理员");
            wangxin.setPassword(initPassword);
            userService.addUser(wangxin, systemRole);

            User lisi = new User();
            lisi.setName("wangxin");
            lisi.setTrueName("王鑫");
            lisi.setEmail("infowangxin@139.com");
            lisi.setPassword(initPassword);
            userService.addUser(lisi, commonRole);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 给角色授权
    private void bindRolePermission() {
        try {
            // 系统管理员
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.user);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.useradd);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.upload);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.ajaxupload);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.springupload);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.download);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.zipupload);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.search);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.jquery_search);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.news);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.newsadd);
            roleService.addRolePermission(Constants.SYSTEM_ROLE_CODE, MenuUtil.news_search);

            // 普通用户
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.upload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.ajaxupload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.springupload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.download);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.zipupload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.search);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.jquery_search);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.news);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.newsadd);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.news_search);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void init() {
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
