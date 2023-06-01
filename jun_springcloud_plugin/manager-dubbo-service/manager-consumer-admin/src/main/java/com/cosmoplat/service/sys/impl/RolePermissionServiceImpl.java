package com.cosmoplat.service.sys.impl;

import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;
import com.cosmoplat.service.sys.RolePermissionProviderService;
import com.cosmoplat.service.sys.RolePermissionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/8/26
 * Description: No Description
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @DubboReference
    RolePermissionProviderService rolePermissionProviderService;

    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {
        rolePermissionProviderService.addRolePermission(vo);
    }

    @Override
    public void save(SysRolePermission sysRolePermission) {
        rolePermissionProviderService.save(sysRolePermission);
    }
}
