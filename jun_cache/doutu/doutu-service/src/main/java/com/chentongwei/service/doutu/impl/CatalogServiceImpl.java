package com.chentongwei.service.doutu.impl;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.entity.doutu.vo.CatalogVO;
import com.chentongwei.service.doutu.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片分类业务实现
 *
 * @author TongWei.Chen 2017-05-17 13:57:42
 */
@Service
public class CatalogServiceImpl implements ICatalogService {

    @Autowired
    private IBasicCache basicCache;

    @Override
    public List<CatalogVO> list() {
        String catalog = basicCache.get("catalog");
        return JSON.parseArray(catalog, CatalogVO.class);
    }
}
