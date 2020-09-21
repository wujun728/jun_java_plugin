package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.ISystemApplicationMonitorDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.SystemApplicationMonitor;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationMonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
@Service
public class SystemApplicationMonitorService extends AbstractBaseService<SystemApplicationMonitor> implements ISystemApplicationMonitorService {
    @Resource
    private ISystemApplicationMonitorDao systemApplicationMonitorDao;

    @Override
    protected IMyBatisRepository<SystemApplicationMonitor> getMyBatisRepository() {
        return systemApplicationMonitorDao;
    }

    @Override
    public PageList<SystemApplicationMonitor> queryEntityPageList(PageAttribute page, SystemApplicationMonitor queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<SystemApplicationMonitor>(systemApplicationMonitorDao)
            .queryPageList(page, queryObject);
    }

}

