package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysRolePermissionMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.model.SysRolePermission;
import com.zhaodui.springboot.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRolePermissionService extends AbstractService<SysRolePermission> {
    @Autowired(required = false)
    private SysRolePermissionMapper sysRolePermissionMapper;


}
