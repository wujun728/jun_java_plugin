package com.jun.plugin.dynamicDataSource.repository;


import java.util.List;

import com.jun.plugin.dynamicDataSource.annotation.DataSource;
import com.jun.plugin.dynamicDataSource.entity.User;

/**
 * @Description: UserMapper接口
 */
public interface UserMapper {

    /**
     * 新增用户
     * @param user
     * @return
     */
    @DataSource  //默认数据源
    int save(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @DataSource("ds1")
    User selectById(Long id);

    /**
     * 查询所有用户信息
     * @return
     */
    @DataSource("ds2")
    List<User> selectAll();
}
