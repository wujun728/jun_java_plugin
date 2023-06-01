package com.cosmoplat.service.sys;

import com.cosmoplat.entity.sys.SysRolePermission;
import com.cosmoplat.entity.sys.vo.req.RolePermissionOperationReqVO;

/**
 * 角色管理
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
public interface RolePermissionService {

    void addRolePermission(RolePermissionOperationReqVO vo);

    void save(SysRolePermission sysRolePermission);
}
