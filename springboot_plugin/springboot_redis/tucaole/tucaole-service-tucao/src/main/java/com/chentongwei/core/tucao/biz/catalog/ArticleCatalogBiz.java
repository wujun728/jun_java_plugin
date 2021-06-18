package com.chentongwei.core.tucao.biz.catalog;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.tucao.entity.vo.catalog.ArticleCatalogListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 分类业务实现
 */
@Service
public class ArticleCatalogBiz {

    @Autowired
    private IBasicCache basicCache;

    /**
     * 获取分类列表
     * @return Result
     */
    public Result list() {
        String catalog = basicCache.get(RedisEnum.getTucaoCatalogKey());
        List<ArticleCatalogListVO> list = JSON.parseArray(catalog, ArticleCatalogListVO.class);
        return ResultCreator.getSuccess(list);
    }

}
