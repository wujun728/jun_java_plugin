package io.github.wujun728.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wujun728.quartz.service.SysJobLogService;
import io.github.wujun728.quartz.entity.SysJobLogEntity;
import io.github.wujun728.quartz.mapper.SysJobLogMapper;

import org.springframework.stereotype.Service;

/**
 * 定时任务 服务类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysJobLogService")
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLogEntity> implements SysJobLogService {


}