package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserRoleMapper;
import com.jun.plugin.springboot.tools.system.model.SysUser;
import com.jun.plugin.springboot.tools.system.model.SysUserRole;

@Service
public class SysUserRoleService extends AbstractService<SysUserRole> {
    @Autowired(required = false)
    private SysUserRoleMapper sysUserRoleMapper;


}
