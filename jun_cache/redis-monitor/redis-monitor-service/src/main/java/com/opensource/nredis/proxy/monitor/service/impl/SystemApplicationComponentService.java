package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.ISystemApplicationComponentDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.SystemApplicationComponent;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationComponentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
@Service
public class SystemApplicationComponentService extends AbstractBaseService<SystemApplicationComponent> implements ISystemApplicationComponentService {
    @Resource
    private ISystemApplicationComponentDao systemApplicationComponentDao;

    @Override
    protected IMyBatisRepository<SystemApplicationComponent> getMyBatisRepository() {
        return systemApplicationComponentDao;
    }

    @Override
    public PageList<SystemApplicationComponent> queryEntityPageList(PageAttribute page, SystemApplicationComponent queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<SystemApplicationComponent>(systemApplicationComponentDao)
            .queryPageList(page, queryObject);
    }

}

