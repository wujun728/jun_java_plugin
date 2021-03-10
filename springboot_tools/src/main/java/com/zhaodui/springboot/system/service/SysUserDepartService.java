package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysUserDepartMapper;
import com.zhaodui.springboot.system.mapper.SysUserRoleMapper;
import com.zhaodui.springboot.system.model.SysUserDepart;
import com.zhaodui.springboot.system.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserDepartService extends AbstractService<SysUserDepart> {
    @Autowired(required = false)
    private SysUserDepartMapper sysUserDepartMapper;


}
