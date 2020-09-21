package com.chentongwei.dao.doutu;

import com.chentongwei.entity.doutu.vo.CatalogVO;

import java.util.List;

/**
 * 图片分类DAO
 *
 * @author TongWei.Chen 2017-05-17 13:44:24
 */
public interface ICatalogDAO {

    /**
     * 获取图片分类列表
     *
     * @return
     */
    List<CatalogVO> list();
}
