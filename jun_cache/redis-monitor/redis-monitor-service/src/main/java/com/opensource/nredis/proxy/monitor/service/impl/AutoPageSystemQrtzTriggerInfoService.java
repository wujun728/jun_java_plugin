package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IAutoPageSystemQrtzTriggerInfoDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.AutoPageSystemQrtzTriggerInfo;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IAutoPageSystemQrtzTriggerInfoService;
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
public class AutoPageSystemQrtzTriggerInfoService extends AbstractBaseService<AutoPageSystemQrtzTriggerInfo> implements IAutoPageSystemQrtzTriggerInfoService {
    @Resource
    private IAutoPageSystemQrtzTriggerInfoDao autoPageSystemQrtzTriggerInfoDao;

    @Override
    protected IMyBatisRepository<AutoPageSystemQrtzTriggerInfo> getMyBatisRepository() {
        return autoPageSystemQrtzTriggerInfoDao;
    }

    @Override
    public PageList<AutoPageSystemQrtzTriggerInfo> queryEntityPageList(PageAttribute page, AutoPageSystemQrtzTriggerInfo queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<AutoPageSystemQrtzTriggerInfo>(autoPageSystemQrtzTriggerInfoDao)
            .queryPageList(page, queryObject);
    }

}

