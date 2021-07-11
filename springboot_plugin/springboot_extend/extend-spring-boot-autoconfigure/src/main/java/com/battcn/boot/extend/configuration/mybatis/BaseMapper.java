package com.battcn.boot.extend.configuration.mybatis;

import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 快速递公共 Mapper
 *
 * @param <T> 实体类
 * @author Levin
 */
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>, ExampleMapper<T> {


}
