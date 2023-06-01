package com.cosmoplat.provider.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmoplat.entity.sys.SysLog;
import com.cosmoplat.provider.mapper.SysLogMapper;
import com.cosmoplat.service.sys.LogProviderService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统日志
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@DubboService
public class LogProviderServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements LogProviderService {
}
