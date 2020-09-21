package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.ISystemApplicationDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.SystemApplication;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.ISystemApplicationService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
@Service
public class SystemApplicationService extends AbstractBaseService<SystemApplication> implements ISystemApplicationService {
    @Resource
    private ISystemApplicationDao systemApplicationDao;

    @Override
    protected IMyBatisRepository<SystemApplication> getMyBatisRepository() {
        return systemApplicationDao;
    }

    @Override
    public PageList<SystemApplication> queryEntityPageList(PageAttribute page, SystemApplication queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<SystemApplication>(systemApplicationDao)
            .queryPageList(page, queryObject);
    }
    
	@Override
	public int updateVersionByIdAndVersion(SystemApplication systemApplication) {
		return systemApplicationDao.updateVersionByIdAndVersion(systemApplication);
	}

	@Override
	public SystemApplication getSystemApplicationByHostAndPort(String host,
			Integer port) throws Exception{
		SystemApplication systemApplication=new SystemApplication();
		systemApplication.setJmxHost(host);
		systemApplication.setJmxPort(port);
		List<SystemApplication> systemApplications=super.queryEntityList(systemApplication);
		if(systemApplications!=null&&systemApplications.size()>0){
			return systemApplications.get(0);
		}
		return null;
	}

}

