package com.cosmoplat.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;

/**
 * 角色权限关联
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RolePermissionProviderService extends IService<SysRolePermission> {

    void addRolePermission(RolePermissionOperationReqVO vo);
}
