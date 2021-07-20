package com.jun.plugin.springboot.tools.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jun.plugin.springboot.tools.common.mapper.BaseMapper;
import com.jun.plugin.springboot.tools.system.model.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);

    @Select("select id from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleIdByUserName(@Param("username") String username);
}