package com.jun.plugin.springboot.tools.system.service;

import org.apache.log4j.net.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springboot.tools.common.service.AbstractService;
import com.jun.plugin.springboot.tools.system.mapper.SysLogMapper;
import com.jun.plugin.springboot.tools.system.mapper.SysUserMapper;
import com.jun.plugin.springboot.tools.system.model.SysLog;
import com.jun.plugin.springboot.tools.system.model.SysUser;

@Service
public class SysLogService extends AbstractService<SysLog> {
    @Autowired(required = false)
    private SysLogMapper sysLogMapper;


}
