package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysRoleMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.model.SysRole;
import com.zhaodui.springboot.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleService extends AbstractService<SysRole> {
    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;


}
