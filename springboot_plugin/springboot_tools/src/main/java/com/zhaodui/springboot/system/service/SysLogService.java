package com.zhaodui.springboot.system.service;

import com.zhaodui.springboot.common.service.AbstractService;
import com.zhaodui.springboot.system.mapper.SysLogMapper;
import com.zhaodui.springboot.system.mapper.SysUserMapper;
import com.zhaodui.springboot.system.model.SysLog;
import com.zhaodui.springboot.system.model.SysUser;
import org.apache.log4j.net.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogService extends AbstractService<SysLog> {
    @Autowired(required = false)
    private SysLogMapper sysLogMapper;


}
