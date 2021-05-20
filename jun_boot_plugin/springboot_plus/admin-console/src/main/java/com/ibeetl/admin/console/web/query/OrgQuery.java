package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;

/**
 * 字典表单查询条件
 */
public class OrgQuery extends PageParam {

    @Query(name = "机构编号", display = true)
    private String code;
    @Query(name = "机构名称", display = true)
    private String name;
	@Query(name="机构类型",display=true,type=Query.TYPE_DICT,dict=CoreDictType.ORG_TYPE)
	private String type;
	@Query(name="上一级机构",display=true,type=Query.TYPE_CONTROL,control="org")
	private String parentOrgId;
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

}
