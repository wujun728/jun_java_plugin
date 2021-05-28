package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.web.query.PageParam;

/**
 */
public class UserRoleQuery extends PageParam {

   
	@Query(name="角色",display=true,type=Query.TYPE_CONTROL,control="role")
    private Long roleId;
    @Query(name="部门",display=true,type=Query.TYPE_CONTROL,control="org")
    private Long orgId;

    private Long userId;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
