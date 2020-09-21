package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IPsUserMenuDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.PsUserMenu;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IPsUserMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/20 18:45
* @version v1.0.0
*/
@Service
public class PsUserMenuService extends AbstractBaseService<PsUserMenu> implements IPsUserMenuService {
    @Resource
    private IPsUserMenuDao psUserMenuDao;

    @Override
    protected IMyBatisRepository<PsUserMenu> getMyBatisRepository() {
        return psUserMenuDao;
    }

    @Override
    public PageList<PsUserMenu> queryEntityPageList(PageAttribute page, PsUserMenu queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<PsUserMenu>(psUserMenuDao)
            .queryPageList(page, queryObject);
    }

}

