package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysPermissionMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.model.SysPermission;
import com.zhaodui.springboot.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionService extends AbstractService<SysPermission> {
    @Autowired(required = false)
    private SysPermissionMapper sysPermissionMapper;


}
