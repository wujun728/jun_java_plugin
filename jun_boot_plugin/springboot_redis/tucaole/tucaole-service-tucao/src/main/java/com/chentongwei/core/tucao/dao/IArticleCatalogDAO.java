package com.chentongwei.core.tucao.dao;


import com.chentongwei.core.tucao.entity.vo.catalog.ArticleCatalogListVO;

import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 分类DAO
 *
 */
public interface IArticleCatalogDAO {

    /**
     * 根据id查询名称 保存吐槽的时候需要验证分类是否存在用
     *
     * @param id：主键id
     * @return
     */
    String getById(Integer id);

    /**
     * 获取分类列表
     *
     * @return List<TucaoCatalogListVO>
     */
    List<ArticleCatalogListVO> list();
}
