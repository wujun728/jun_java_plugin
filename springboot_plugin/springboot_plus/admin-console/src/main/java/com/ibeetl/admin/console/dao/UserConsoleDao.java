package com.ibeetl.admin.console.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.entity.CoreUserRole;
import com.ibeetl.admin.core.util.enums.GeneralStateEnum;

import java.util.List;

@SqlResource("console.user")
public interface UserConsoleDao extends BaseMapper<CoreUser> {

    PageQuery<CoreUser> queryByCondtion(PageQuery<CoreUser> query);

    void batchDelUserByIds( List<Long> ids);

    void batchUpdateUserState(List<Long> ids, GeneralStateEnum state);
    
    List<CoreUserRole> queryUserRole( Long id,Long orgId,Long roleId);

}

