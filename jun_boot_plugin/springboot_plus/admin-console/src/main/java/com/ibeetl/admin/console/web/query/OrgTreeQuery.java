package com.ibeetl.admin.console.web.query;

import java.util.List;

import com.ibeetl.admin.core.web.query.PageParam;

/**
 * 描述: 带有组织树相关的查询
 */
public class OrgTreeQuery extends PageParam {
    protected Long orgId; //组织id
    protected List<Long> orgIds;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }
}
