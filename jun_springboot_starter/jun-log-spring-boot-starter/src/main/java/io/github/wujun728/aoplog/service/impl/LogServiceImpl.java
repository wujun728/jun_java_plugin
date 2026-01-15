package io.github.wujun728.aoplog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wujun728.aoplog.entity.SysLog;
import io.github.wujun728.aoplog.mapper.SysLogMapper;
import io.github.wujun728.aoplog.service.LogService;
import org.springframework.stereotype.Service;

/**
 * 系统日志
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
public class LogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements LogService {
}
