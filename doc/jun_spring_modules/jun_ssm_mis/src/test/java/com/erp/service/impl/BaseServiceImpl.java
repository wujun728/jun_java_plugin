package com.erp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.model2.Syresources;
import com.erp.model2.Syuser;
import com.erp.service.IBaseService;

/**
 * 类功能说明 TODO: 类修改者 修改日期 修改说明
 * <p>
 * Title: BaseServiceImpl.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company:福产流通科技
 * </p>
 * 
 * @author Wujun
 * @date 2013-4-19 下午03:18:37
 * @version V1.0
 */
// @Service("baseService")
// @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

@Service("baseService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseServiceImpl implements IBaseService {
	private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);
	@SuppressWarnings("rawtypes")
	private IBaseDao resourcesDao2;
	private IBaseDao userDao2;

	public IBaseDao<Syuser> getUserDao() {
		return userDao2;
	}

	@Autowired
	public void setUserDao(IBaseDao<Syuser> userDao) {
		this.userDao2 = userDao;
	}

	public IBaseDao<Syresources> getResourcesDao() {
		return resourcesDao2;
	}

	@Autowired
	public void setResourcesDao(IBaseDao<Syresources> resourcesDao) {
		this.resourcesDao2 = resourcesDao;
	}

	@Cacheable(value = "syproAuthCache", key = "'offResourcesList'")
	@Transactional(readOnly = true)
	public List<Syresources> offResourcesList() {
		return resourcesDao2.find("from Syresources t where t.onoff != '1'");
	}

	@Cacheable(value = "syproAuthCache", key = "'getSyresourcesByRequestPath'+#requestPath")
	@Transactional(readOnly = true)
	public Object getSyresourcesByRequestPath(String requestPath) {
		return resourcesDao2.get("from Syresources t where t.src=?", requestPath);
	}

	@Cacheable(value = "syproAuthCache", key = "'checkAuth'+#userId+#requestPath")
	@Transactional(readOnly = true)
	public boolean checkAuth(String userId, String requestPath) {
		boolean b = false;
		Object syuser = userDao2.get(Syuser.class, userId);
		return b;
	}

}
