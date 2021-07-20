package com.jun.plugin.mybatis.mapper.auth;

import org.apache.ibatis.annotations.Mapper;

import com.jun.plugin.mybatis.mapper.BaseMapper;
import com.jun.plugin.mybatis.model.auth.RolePermission;

/** 
 * @Description 角色与菜单关系Mapper
 * @author Wujun
 * @date Apr 12, 2017 9:13:04 AM  
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<String, RolePermission> {

    public RolePermission findRolePermission(RolePermission per);

}
