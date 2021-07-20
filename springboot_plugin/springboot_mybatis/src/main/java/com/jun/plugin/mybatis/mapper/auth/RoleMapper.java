package com.jun.plugin.mybatis.mapper.auth;

import org.apache.ibatis.annotations.Mapper;

import com.jun.plugin.mybatis.mapper.BaseMapper;
import com.jun.plugin.mybatis.model.auth.Role;

import java.util.List;

/** 
 * @Description 角色Mapper
 * @author Wujun
 * @date Apr 12, 2017 9:12:42 AM  
 */
@Mapper
public interface RoleMapper extends BaseMapper<String, Role> {

    /**
     * 根据用户查询对应所有角色
     *
     * @param userId
     *            用户
     * @return roles 所有角色
     */
    public List<Role> findRoleByUserId(String userId);

    /**
     * 根据编码查询角色
     *
     * @param code
     *            角色编码
     * @return
     */
    public Role findRoleByCode(String code);

}
