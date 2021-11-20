package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysRolePermissionMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysRolePermission;
import com.jun.plugin.springboot.tools.system.model.SysUser;

@Service
public class SysRolePermissionService extends AbstractService<SysRolePermission> {
    @Autowired(required = false)
    private SysRolePermissionMapper sysRolePermissionMapper;


}
