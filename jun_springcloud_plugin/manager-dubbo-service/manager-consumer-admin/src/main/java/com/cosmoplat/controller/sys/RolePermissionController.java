package com.cosmoplat.controller.sys;

import com.cosmoplat.common.aop.annotation.LogAnnotation;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;
import com.cosmoplat.service.sys.RolePermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 角色和菜单关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@RequestMapping("/sys")
@RestController
public class RolePermissionController {
    @Resource
    private RolePermissionService rolePermissionService;

    @PostMapping("/role/permission")
    @LogAnnotation(title = "角色和菜单关联接口", action = "修改或者新增角色菜单权限")
//    @RequiresPermissions(value = {"sys:role:update", "sys:role:add"}, logical = Logical.OR)
    public DataResult operationRolePermission(@RequestBody @Valid RolePermissionOperationReqVO vo) {
        rolePermissionService.addRolePermission(vo);
        return DataResult.success();
    }
}
