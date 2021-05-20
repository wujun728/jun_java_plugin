package cn.classg.service;

import cn.classg.entity.Category;

import java.util.List;

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
