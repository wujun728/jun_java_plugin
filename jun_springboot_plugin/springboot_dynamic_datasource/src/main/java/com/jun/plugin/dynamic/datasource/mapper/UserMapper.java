package com.jun.plugin.dynamic.datasource.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.jun.plugin.dynamic.datasource.config.MyMapper;
import com.jun.plugin.dynamic.datasource.model.User;

/**
 * <p>
 * 用户 Mapper
 * </p>
 *
 * @author Wujun
 * @date Created in 2019/9/4 16:49
 */
@Mapper
public interface UserMapper extends MyMapper<User> {
}
