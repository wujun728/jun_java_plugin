package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IRedisClusterMasterDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMaster;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IRedisClusterMasterService;
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
public class RedisClusterMasterService extends AbstractBaseService<RedisClusterMaster> implements IRedisClusterMasterService {
    @Resource
    private IRedisClusterMasterDao redisClusterMasterDao;

    @Override
    protected IMyBatisRepository<RedisClusterMaster> getMyBatisRepository() {
        return redisClusterMasterDao;
    }

    @Override
    public PageList<RedisClusterMaster> queryEntityPageList(PageAttribute page, RedisClusterMaster queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<RedisClusterMaster>(redisClusterMasterDao)
            .queryPageList(page, queryObject);
    }

}

