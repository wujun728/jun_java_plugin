package com.cosmoplat.controller.sys;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.exception.BusinessException;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.Constant;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.SysPermission;
import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.service.sys.HttpSessionService;
import com.cosmoplat.service.sys.PermissionService;
import com.cosmoplat.service.sys.RolePermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * 菜单权限管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys/permissions")
@RestController
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private HttpSessionService httpSessionService;

    @PostMapping("/add")
    @LogAnnotation(title = "菜单权限管理", action = "新增菜单权限")
//    @RequiresPermissions("sys:permission:add")
    public DataResult addPermission(@RequestBody @Valid SysPermission vo) {
        verifyFormPid(vo);
        //创建人
        vo.setCreateId(httpSessionService.getCurrentUserId());
        //定义id
        String id = String.valueOf(IdWorker.getId());
        vo.setId(id);
        permissionService.save(vo);
        //默认绑定到超级管理员角色上
        SysRolePermission rolePermission = new SysRolePermission().setPermissionId(id).setRoleId(Constant.SUPER_ROLE_ID).setCreateTime(new Date());
        rolePermissionService.save(rolePermission);
        return DataResult.success();
    }

    @PostMapping("/delete/{id}")
    @LogAnnotation(title = "菜单权限管理", action = "删除菜单权限")
    @RequiresPermissions("sys:permission:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        permissionService.deleted(id);
        return DataResult.success();
    }

    @PostMapping("/update")
    @LogAnnotation(title = "菜单权限管理", action = "更新菜单权限")
//    @RequiresPermissions("sys:permission:update")
    public DataResult updatePermission(@RequestBody @Valid SysPermission vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        SysPermission sysPermission = permissionService.getById(vo.getId());
        if (null == sysPermission) {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        // 只有类型变更或者所属菜单变更
        if (sysPermission.getType().equals(vo.getType()) || !sysPermission.getPid().equals(vo.getPid())) {
            verifyFormPid(vo);
        }
        //创建人
        vo.setUpdateId(httpSessionService.getCurrentUserId());
        permissionService.updateById(vo);
        return DataResult.success();
    }

    @GetMapping("/{id}")
    @LogAnnotation(title = "菜单权限管理", action = "查询菜单权限")
//    @RequiresPermissions("sys:permission:detail")
    public DataResult detailInfo(@PathVariable("id") String id) {
        return DataResult.success(permissionService.getById(id));

    }

    @GetMapping("/")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有菜单权限")
//    @RequiresPermissions("sys:permission:list")
    public DataResult getAllMenusPermission() {
        return DataResult.success(permissionService.selectAll());
    }

    @GetMapping("/tree")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有目录菜单树")
//    @RequiresPermissions(value = {"sys:permission:update", "sys:permission:add"}, logical = Logical.OR)
    public DataResult getAllMenusPermissionTree(@RequestParam(required = false) String permissionId) {
        return DataResult.success(permissionService.selectAllMenuByTree(permissionId));
    }

    @GetMapping("/tree/all")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有目录菜单树")
//    @RequiresPermissions(value = {"sys:role:update", "sys:role:add"}, logical = Logical.OR)
    public DataResult getAllPermissionTree() {
        return DataResult.success(permissionService.selectAllByTree());
    }

    /**
     * 操作后的菜单类型是目录的时候 父级必须为目录
     * 操作后的菜单类型是菜单的时候，父类必须为目录类型
     * 操作后的菜单类型是按钮的时候 父类必须为菜单类型
     */
    private void verifyFormPid(SysPermission sysPermission) {
        SysPermission parent;
        parent = permissionService.getById(sysPermission.getPid());
        switch (sysPermission.getType()) {
            case 1:
                if (parent != null) {
                    if (parent.getType() != 1) {
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                } else if (!"0".equals(sysPermission.getPid())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
//                if (parent == null || parent.getType() != 1) {
//                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
//                }
                if (StringUtils.isEmpty(sysPermission.getUrl())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }

                break;
            case 3:
                if (parent == null || parent.getType() != 2) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if (StringUtils.isEmpty(sysPermission.getPerms())) {
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                break;
            default:
        }
    }
}
