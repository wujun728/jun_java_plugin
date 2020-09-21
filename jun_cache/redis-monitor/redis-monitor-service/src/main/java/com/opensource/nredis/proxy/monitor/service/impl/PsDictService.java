package com.opensource.nredis.proxy.monitor.service.impl;

import com.opensource.nredis.proxy.monitor.dao.IPsDictDao;
import com.opensource.nredis.proxy.monitor.dao.IMyBatisRepository;
import com.opensource.nredis.proxy.monitor.model.PsDict;
import com.opensource.nredis.proxy.monitor.model.pagination.PageAttribute;
import com.opensource.nredis.proxy.monitor.model.pagination.PageList;
import com.opensource.nredis.proxy.monitor.model.pagination.PaginationTemplate;
import com.opensource.nredis.proxy.monitor.service.AbstractBaseService;
import com.opensource.nredis.proxy.monitor.service.IPsDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
* service impl
*
* @author liubing
* @date 2016/12/21 17:32
* @version v1.0.0
*/
@Service
public class PsDictService extends AbstractBaseService<PsDict> implements IPsDictService {
    @Resource
    private IPsDictDao psDictDao;

    @Override
    protected IMyBatisRepository<PsDict> getMyBatisRepository() {
        return psDictDao;
    }

    @Override
    public PageList<PsDict> queryEntityPageList(PageAttribute page, PsDict queryObject, Map<String, Object> otherParam) {
        return new PaginationTemplate<PsDict>(psDictDao)
            .queryPageList(page, queryObject);
    }

}

