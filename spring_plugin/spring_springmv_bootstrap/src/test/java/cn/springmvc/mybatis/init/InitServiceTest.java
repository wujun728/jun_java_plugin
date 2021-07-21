package cn.springmvc.mybatis.init;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.entity.auth.Permission;
import cn.springmvc.mybatis.entity.auth.Role;
import cn.springmvc.mybatis.entity.auth.User;
import cn.springmvc.mybatis.service.auth.PermissionService;
import cn.springmvc.mybatis.service.auth.RoleService;
import cn.springmvc.mybatis.service.auth.UserService;
import cn.springmvc.mybatis.web.util.MenuUtil;

/**
 * @author Vincent.wang
 *
 *         production为生产环境，development为测试环境
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-shiro.xml", "classpath:/spring/applicationContext-activiti.xml" })
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
            Role boss = new Role();
            boss.setName(Constants.ROLE_BOSS_NAME);// 系统管理员
            boss.setCode(Constants.ROLE_BOSS_CODE);
            boss.setRemark(Constants.ROLE_BOSS_NAME);
            roleService.addRole(boss);

            Role system = new Role();
            system.setName(Constants.ROLE_MANAGER_NAME);// 系统管理员
            system.setCode(Constants.ROLE_MANAGER_CODE);
            system.setRemark(Constants.ROLE_MANAGER_NAME);
            roleService.addRole(system);

            Role common = new Role();
            common.setName(Constants.ROLE_COMMON_NAME);// 普通用户
            common.setCode(Constants.COMMON_ROLE_CODE);
            common.setRemark(Constants.ROLE_COMMON_NAME);
            roleService.addRole(common);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建菜单
     */
    private void addPermission() {
        try {
            List<Permission> permis = MenuUtil.importPermissionData();
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
            Role boss = roleService.findRoleByCode(Constants.ROLE_BOSS_CODE);// 总经理
            Role manager = roleService.findRoleByCode(Constants.ROLE_MANAGER_CODE);// 管理员
            Role common = roleService.findRoleByCode(Constants.COMMON_ROLE_CODE);// 普通用户

            String password = "123456";

            User bossUser = new User();
            bossUser.setName("wzj");
            bossUser.setEmail("wangzhongjun@163.com");
            bossUser.setTrueName("王中军");
            bossUser.setPassword(password);
            bossUser.setOrganizeId(boss.getId());
            userService.addUser(bossUser, boss);

            User fxgManager = new User();
            fxgManager.setName("fxg_manager");
            fxgManager.setTrueName("冯小刚经纪人");
            fxgManager.setEmail("fengxiaogangManager@139.com");
            fxgManager.setPassword(password);
            fxgManager.setOrganizeId(manager.getId());
            userService.addUser(fxgManager, manager);

            User fbbManager = new User();
            fbbManager.setName("fbb_manager");
            fbbManager.setTrueName("范冰冰经纪人");
            fbbManager.setEmail("fanbingbingManager@139.com");
            fbbManager.setPassword(password);
            fbbManager.setOrganizeId(boss.getId());
            userService.addUser(fbbManager, manager);

            User fxgUser = new User();
            fxgUser.setName("fxg");
            fxgUser.setTrueName("冯小刚");
            fxgUser.setEmail("fengxiaogang@139.com");
            fxgUser.setPassword(password);
            fxgUser.setOrganizeId(manager.getId());
            userService.addUser(fxgUser, common);

            User fbbUser = new User();
            fbbUser.setName("fbb");
            fbbUser.setTrueName("范冰冰");
            fbbUser.setEmail("fanbingbing@139.com");
            fbbUser.setPassword(password);
            fbbUser.setOrganizeId(manager.getId());
            userService.addUser(fbbUser, common);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 给角色授权
    private void bindRolePermission() {
        try {
            // 总经理
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.leaveBill);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.leaveBill_home);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.workflow);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.workflow_deployHome);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.workflow_listTask);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.user);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.useradd);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.upload);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.ajaxupload);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.springupload);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.import_excel);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.download);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.zipupload);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.search);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.jquery_search);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.news);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.newsadd);
            roleService.addRolePermission(Constants.ROLE_BOSS_CODE, MenuUtil.news_search);

            // 管理员
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.leaveBill);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.leaveBill_home);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.workflow);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.workflow_deployHome);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.workflow_listTask);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.user);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.useradd);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.upload);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.ajaxupload);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.springupload);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.import_excel);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.download);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.zipupload);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.search);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.jquery_search);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.news);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.newsadd);
            roleService.addRolePermission(Constants.ROLE_MANAGER_CODE, MenuUtil.news_search);

            // 普通用户
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.leaveBill);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.leaveBill_home);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.workflow);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.workflow_deployHome);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.workflow_listTask);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.upload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.ajaxupload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.springupload);
            roleService.addRolePermission(Constants.COMMON_ROLE_CODE, MenuUtil.import_excel);
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
