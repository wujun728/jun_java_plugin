package com.cosmoplat.controller.sys;

import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.SysRole;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys/role")
@RestController
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping("/add")
    @LogAnnotation(title = "角色管理", action = "新增角色")
//    @RequiresPermissions("sys:role:add")
    public DataResult addRole(@RequestBody @Valid SysRole vo) {

        vo.setCreateId(httpSessionService.getCurrentUserId());
        roleService.addRole(vo);
        return DataResult.success();
    }

    @PostMapping("/delete/{id}")
    @LogAnnotation(title = "角色管理", action = "删除角色")
    @RequiresPermissions("sys:role:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        if (Constant.SUPER_ROLE_ID.equals(id)) {
            return DataResult.fail("超级管理员角色不能删除!");
        }
        roleService.deletedRole(id);
        return DataResult.success();
    }

    @PostMapping("/update")
    @LogAnnotation(title = "角色管理", action = "更新角色信息")
//    @RequiresPermissions("sys:role:update")
    public DataResult updateDept(@RequestBody SysRole vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        vo.setUpdateId(httpSessionService.getCurrentUserId());
        roleService.updateRole(vo);
        return DataResult.success();
    }

    @GetMapping("/{id}")
    @LogAnnotation(title = "角色管理", action = "查询角色详情")
//    @RequiresPermissions("sys:role:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(roleService.detailInfo(id));
    }

    @GetMapping("/listByPage")
    @LogAnnotation(title = "角色管理", action = "分页获取角色信息")
//    @RequiresPermissions("sys:role:list")
    public DataResult pageInfo(SysRole vo) {
        return DataResult.success(roleService.pageInfo(vo));
    }

}
