package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.mapper.SysUserRoleMapper;
import com.zhaodui.springboot.system.model.SysUser;
import com.zhaodui.springboot.system.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleService extends AbstractService<SysUserRole> {
    @Autowired(required = false)
    private SysUserRoleMapper sysUserRoleMapper;


}
