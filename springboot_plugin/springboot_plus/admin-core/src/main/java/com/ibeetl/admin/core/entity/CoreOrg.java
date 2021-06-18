package com.ibeetl.admin.core.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.annotation.Dict;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.util.enums.CoreDictType;


/**
 * 机构对象，
 * 有母公司，一个系统只有一个母公司，多个集团，集团下可以有多个公司，子公司，部门。如果系统不符合这个设定，需要修改·
 * 
 * <br/>
 * 映射了上级机构，可以通过org.parentOrg.xxx取上级机构的属性
 */

public class CoreOrg extends BaseEntity {
    
    // 自增id
	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
    private Long id;

    //删除标识
    @JsonIgnore
    protected Integer delFlag= 0;
    //创建时间
    protected Date createTime;

    // 机构编号
    @NotBlank(message = "组织编号不能为空", groups = ValidateConfig.ADD.class)
    private String code;

    // 机构名称
    @NotBlank(message = "组织名称不能为空", groups = {ValidateConfig.ADD.class, ValidateConfig.UPDATE.class})
    private String name;

    // 上层机构id
    private Long parentOrgId;

    // 机构类型 1 集团 2 公司，3 部门，4 小组
    @Dict(type = CoreDictType.ORG_TYPE)
    @NotBlank(message = "组织类型不能为空", groups = ValidateConfig.class)
    private String type;

  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

   

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

   
}
