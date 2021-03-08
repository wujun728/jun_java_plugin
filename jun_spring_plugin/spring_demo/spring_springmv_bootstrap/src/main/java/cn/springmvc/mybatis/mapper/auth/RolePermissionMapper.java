package cn.springmvc.mybatis.mapper.auth;

import cn.springmvc.mybatis.entity.auth.RolePermission;
import cn.springmvc.mybatis.mapper.BaseMapper;

public interface RolePermissionMapper extends BaseMapper<String, RolePermission> {

    public RolePermission findRolePermission(RolePermission per);

}
