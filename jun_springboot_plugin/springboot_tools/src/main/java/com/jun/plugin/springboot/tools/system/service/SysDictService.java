package com.jun.plugin.springboot.tools.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysDictMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysDict;
import com.jun.plugin.springboot.tools.system.model.SysUser;

@Service
public class SysDictService extends AbstractService<SysDict> {
    @Autowired(required = false)
    private SysDictMapper sysDictMapper;


}
