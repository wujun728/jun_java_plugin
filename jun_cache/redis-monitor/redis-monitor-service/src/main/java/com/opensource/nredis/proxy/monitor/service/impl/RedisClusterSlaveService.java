package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IRedisClusterSlaveDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.RedisClusterSlave;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterSlaveService;
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
public class RedisClusterSlaveService extends AbstractBaseService<RedisClusterSlave> implements IRedisClusterSlaveService {
    @Resource
    private IRedisClusterSlaveDao redisClusterSlaveDao;

    @Override
    protected IMyBatisRepository<RedisClusterSlave> getMyBatisRepository() {
        return redisClusterSlaveDao;
    }

    @Override
    public PageList<RedisClusterSlave> queryEntityPageList(PageAttribute page, RedisClusterSlave queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<RedisClusterSlave>(redisClusterSlaveDao)
            .queryPageList(page, queryObject);
    }

}

