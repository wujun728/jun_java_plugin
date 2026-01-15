package io.github.wujun728.snakerflow.ext.service.impl;

import io.github.wujun728.snakerflow.ext.entity.ExtLog;
import io.github.wujun728.snakerflow.ext.mapper.ExtLogMapper;
import io.github.wujun728.snakerflow.ext.service.IExtLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * 
 * @since 2021-08-16
 */
@Service
public class ExtLogServiceImpl extends ServiceImpl<ExtLogMapper, ExtLog> implements IExtLogService {

}
