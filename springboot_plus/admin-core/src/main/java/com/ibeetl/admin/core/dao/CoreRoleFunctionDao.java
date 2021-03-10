package com.ibeetl.admin.core.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreRoleFunction;

import java.util.List;

@SqlResource("core.coreRoleFunction")
public interface CoreRoleFunctionDao extends BaseMapper<CoreRoleFunction> {


    List<CoreRoleFunction> getRoleFunction( Long userId, Long orgId,
                                         String code);

    List<String> getRoleChildrenFunction(Long userId,  Long orgId,
                                          Long parentId);


}
