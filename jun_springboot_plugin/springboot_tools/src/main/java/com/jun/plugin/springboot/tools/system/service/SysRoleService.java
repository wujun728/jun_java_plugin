package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysRoleMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysRole;
import com.jun.plugin.springboot.tools.system.model.SysUser;

@Service
public class SysRoleService extends AbstractService<SysRole> {
    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;


}
