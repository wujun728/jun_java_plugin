package com.jun.plugin.orm.beetlsql.dao;

import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import com.jun.plugin.orm.beetlsql.entity.User;

/**
 * <p>
 * UserDao
 * </p>
 *
 * @package: com.xkcoding.orm.beetlsql.dao
 * @description: UserDao
 * @author: yangkai.shen
 * @date: Created in 2018/11/14 16:18
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public interface UserDao extends BaseMapper<User> {

}
