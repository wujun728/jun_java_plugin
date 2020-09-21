package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IRedisClusterMonitorLogDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMonitorLog;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterMonitorLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
@Service
public class RedisClusterMonitorLogService extends AbstractBaseService<RedisClusterMonitorLog> implements IRedisClusterMonitorLogService {
    @Resource
    private IRedisClusterMonitorLogDao redisClusterMonitorLogDao;

    @Override
    protected IMyBatisRepository<RedisClusterMonitorLog> getMyBatisRepository() {
        return redisClusterMonitorLogDao;
    }

    @Override
    public PageList<RedisClusterMonitorLog> queryEntityPageList(PageAttribute page, RedisClusterMonitorLog queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<RedisClusterMonitorLog>(redisClusterMonitorLogDao)
            .queryPageList(page, queryObject);
    }

}

