package com.ibeetl.admin.core.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreRoleMenu;

import java.util.List;

@SqlResource("core.coreRoleMenu")
public interface CoreRoleMenuDao extends BaseMapper<CoreRoleMenu> {

    /**
     * 根据用户ID，机构ID查询菜单
     * @param userId 用户id
     * @param orgId  机构id
     * @return
     */
    List<Long> queryMenuByUser( Long userId, Long orgId);

    /**
     * 根据菜单id删除角色和菜单关系
     * @param ids
     */
    void deleteRoleMenu(List<Long> ids);
}
