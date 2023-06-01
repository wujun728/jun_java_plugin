package com.soukuan.web.tmpl;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * Title mybatis通用Mapper接口（所有通用mapper需继承此接口）
 * DateTime  17-4-5.
 * Version V1.0.0
 */
public interface IBaseCommMapper<T> extends Mapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListMapper<T> {
}
