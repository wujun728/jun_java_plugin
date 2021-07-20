package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysUserDepartMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserRoleMapper;
import com.jun.plugin.springboot.tools.system.model.SysUserDepart;
import com.jun.plugin.springboot.tools.system.model.SysUserRole;

@Service
public class SysUserDepartService extends AbstractService<SysUserDepart> {
    @Autowired(required = false)
    private SysUserDepartMapper sysUserDepartMapper;


}
