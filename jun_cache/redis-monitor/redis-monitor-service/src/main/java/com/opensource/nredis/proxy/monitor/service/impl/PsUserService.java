package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IPsUserDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.PsUser;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IPsUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
@Service
public class PsUserService extends AbstractBaseService<PsUser> implements IPsUserService {
    @Resource
    private IPsUserDao psUserDao;

    @Override
    protected IMyBatisRepository<PsUser> getMyBatisRepository() {
        return psUserDao;
    }

    @Override
    public PageList<PsUser> queryEntityPageList(PageAttribute page, PsUser queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<PsUser>(psUserDao)
            .queryPageList(page, queryObject);
    }

}

