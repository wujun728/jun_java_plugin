package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IPsMenuDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.PsMenu;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IPsMenuService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
@Service
public class PsMenuService extends AbstractBaseService<PsMenu> implements IPsMenuService {
    @Resource
    private IPsMenuDao psMenuDao;

    @Override
    protected IMyBatisRepository<PsMenu> getMyBatisRepository() {
        return psMenuDao;
    }

    @Override
    public PageList<PsMenu> queryEntityPageList(PageAttribute page, PsMenu queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<PsMenu>(psMenuDao)
            .queryPageList(page, queryObject);
    }

	@Override
	public List<PsMenu> getMenusByUserId(PsMenu psMenu) {
		return psMenuDao.getMenusByUserId(psMenu);
	}

}

