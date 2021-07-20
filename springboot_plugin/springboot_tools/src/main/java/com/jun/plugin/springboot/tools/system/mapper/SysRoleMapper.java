package com.jun.plugin.springboot.tools.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.jun.plugin.springboot.tools.common.mapper.BaseMapper;
import com.jun.plugin.springboot.tools.system.model.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * @Description: 删除角色与用户关系
     */
    @Delete("delete from sys_user_role where role_id = #{roleId}")
    void deleteRoleUserRelation(@Param("roleId") String roleId);


    /**
      * @Description: 删除角色与权限关系
     */
    @Delete("delete from sys_role_permission where role_id = #{roleId}")
    void deleteRolePermissionRelation(@Param("roleId") String roleId);
}