package com.chentongwei.core.tucao.biz.catalog;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.core.tucao.dao.IArticleCatalogDAO;
import com.chentongwei.core.tucao.entity.vo.catalog.ArticleCatalogListVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 容器启动时执行的Service。负责将一些data缓存到redis
 */
@Service
public class InitArticleCatalogBiz {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private IArticleCatalogDAO catalogDAO;
    @Autowired
    private IBasicCache basicCache;

    /**
     * 缓存分类列表
     */
    public void initCatalog() {
        List<ArticleCatalogListVO> list = catalogDAO.list();
        basicCache.set(RedisEnum.getTucaoCatalogKey(), JSON.toJSONString(list));
        LOG.info("缓存分类信息完成");
    }
}