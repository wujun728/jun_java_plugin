package com.ssm.dubbo.api.user.service;

import com.ssm.dubbo.api.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
public interface UserService {

    /**
     * @param user
     * @return
     */
    User login(User user);

    /**
     * @param map
     * @return
     */
    List<User> findUser(Map<String, Object> map);

    /**
     * @param map
     * @return
     */
    Long getTotalUser(Map<String, Object> map);

    /**
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * @param id
     * @return
     */
    int deleteUser(Integer id);
}
