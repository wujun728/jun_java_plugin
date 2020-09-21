package com.chentongwei.service.doutu;

import com.chentongwei.entity.doutu.vo.CatalogVO;

import java.util.List;

/**
 * 图片分类业务层
 *
 * @author TongWei.Chen 2017-05-17 13:56:08
 */
public interface ICatalogService {

    /**
     * 图片分类列表查询,查redis
     *
     * @return
     */
    List<CatalogVO> list();
}
