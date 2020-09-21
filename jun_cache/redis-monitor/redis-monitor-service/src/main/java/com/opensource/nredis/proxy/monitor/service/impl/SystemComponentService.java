package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.ISystemComponentDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.SystemComponent;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.ISystemComponentService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
@Service
public class SystemComponentService extends AbstractBaseService<SystemComponent> implements ISystemComponentService {
    @Resource
    private ISystemComponentDao systemComponentDao;

    @Override
    protected IMyBatisRepository<SystemComponent> getMyBatisRepository() {
        return systemComponentDao;
    }

    @Override
    public PageList<SystemComponent> queryEntityPageList(PageAttribute page, SystemComponent queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<SystemComponent>(systemComponentDao)
            .queryPageList(page, queryObject);
    }

	@Override
	public List<SystemComponent> getListByApplicationId(Integer applicationId) {
		return systemComponentDao.getListByApplicationId(applicationId);
	}

}

