package com.mall.admin.controller.system;

import com.mall.admin.service.MenuService;
import com.mall.admin.service.RoleService;
import com.mall.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2414:55
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 角色列表
     * @param params
     * @return
     */
    @GetMapping
    public List<Map> list(@RequestParam Map params) {
        return roleService.list(params);
    }

    /**
     * 获取指定角色菜单
     * @param roleId
     * @return
     */
    @GetMapping("/getMenuByRole")
    public List<Map> getMenuByRole(int roleId) {
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        return menuService.getMenuByRole(roleIds);
    }
}
