package com.ssm.dubbo.service.user.dao;

import com.ssm.dubbo.api.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
@Repository
public interface UserDao {

    /**
     * 登录
     *
     * @param user
     * @return
     */
     User login(User user);

    /**
     * 查找用户列表
     *
     * @param map
     * @return
     */
     List<User> findUsers(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
     Long getTotalUser(Map<String, Object> map);

    /**
     * 实体修改
     *
     * @param user
     * @return
     */
     int updateUser(User user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
     int addUser(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
     int deleteUser(Integer id);
}
