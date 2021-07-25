package com.jun.plugin.service;

import java.util.List;

import com.jun.plugin.entity.Category;

public interface CategoryService {

    /**
     * 查询所有种类
     * @return
     */
    List<Category> queryAllCategory();

    /**
     * 添加种类
     * @param category
     * @return
     */
    Integer insertGagegory(Category category);
}
