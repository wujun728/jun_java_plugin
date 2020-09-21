package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IAutoPageSystemQrtzTriggerLogDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerLog;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/30 08:41
* @version v1.0.0
*/
@Service
public class AutoPageSystemQrtzTriggerLogService extends AbstractBaseService<AutoPageSystemQrtzTriggerLog> implements IAutoPageSystemQrtzTriggerLogService {
    @Resource
    private IAutoPageSystemQrtzTriggerLogDao autoPageSystemQrtzTriggerLogDao;

    @Override
    protected IMyBatisRepository<AutoPageSystemQrtzTriggerLog> getMyBatisRepository() {
        return autoPageSystemQrtzTriggerLogDao;
    }

    @Override
    public PageList<AutoPageSystemQrtzTriggerLog> queryEntityPageList(PageAttribute page, AutoPageSystemQrtzTriggerLog queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<AutoPageSystemQrtzTriggerLog>(autoPageSystemQrtzTriggerLogDao)
            .queryPageList(page, queryObject);
    }

}

