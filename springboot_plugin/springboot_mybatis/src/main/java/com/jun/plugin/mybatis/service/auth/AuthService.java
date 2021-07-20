package com.jun.plugin.mybatis.service.auth;

import java.util.List;

import com.jun.plugin.mybatis.model.auth.Role;
import com.jun.plugin.mybatis.model.auth.User;

/** 
 * @Description 组装权限接口
 * @author Wujun
 * @date Apr 12, 2017 9:14:26 AM  
 */
public interface AuthService {

    /**
     * 根据用户名查询用户
     *
     * @param username
     *            用户名
     * @return user 用户
     */
    public User findUserByName(String username);

    /**
     * 根据角色编码查询角色
     * 
     * @param roleCode
     *            角色编码
     * @return 角色对象
     */
    public Role findRoleByRoleCode(String roleCode);

    /**
     * 根据角色编码查询用户
     *
     * @param roleCode
     *            角色编码
     * @return user 用户
     */
    public List<User> findUserByRoleCode(String roleCode);

}
