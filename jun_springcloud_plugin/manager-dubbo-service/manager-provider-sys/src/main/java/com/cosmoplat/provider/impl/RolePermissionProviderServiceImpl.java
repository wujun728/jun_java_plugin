package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;
import com.cosmoplat.provider.mapper.SysRolePermissionMapper;
import com.cosmoplat.service.sys.RolePermissionProviderService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
public class RolePermissionProviderServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements RolePermissionProviderService {
    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        List<SysRolePermission> list = new ArrayList<>();
        for (String permissionId : vo.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(vo.getRoleId());
            list.add(sysRolePermission);
        }
        this.remove(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, vo.getRoleId()));
        this.saveBatch(list);
    }

}
