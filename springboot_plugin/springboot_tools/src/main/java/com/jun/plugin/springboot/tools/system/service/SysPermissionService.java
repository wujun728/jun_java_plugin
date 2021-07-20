package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysPermissionMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysPermission;
import com.jun.plugin.springboot.tools.system.model.SysUser;

@Service
public class SysPermissionService extends AbstractService<SysPermission> {
    @Autowired(required = false)
    private SysPermissionMapper sysPermissionMapper;


}
