package com.ibeetl.admin.core.entity;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.UpdateIgnore;

import com.ibeetl.admin.core.annotation.Dict;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.util.enums.CoreDictType;


public class CoreFunction extends BaseEntity   {
	
	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
    protected Long id;

    //创建时间
	@UpdateIgnore
    protected Date createTime;
	private String accessUrl ;
	@NotBlank
	private String code ;
    @NotBlank
	private String name ;
	@NotBlank
	private Long parentId ;
	@Dict(type=CoreDictType.FUNCTION_TYPE)
	@NotBlank
	private String type = null;//"FN0" ;
	

	public String getAccessUrl(){
		return  accessUrl;
	}
	public void setAccessUrl(String accessUrl ){
		this.accessUrl = accessUrl;
	}
	
	public String getCode(){
		return  code;
	}
	public void setCode(String code ){
		this.code = code;
	}
	
	public String getName(){
		return  name;
	}
	public void setName(String name ){
		this.name = name;
	}
	
	public Date getCreateTime(){
		return  createTime;
	}
	public void setCreateTime(Date createTime ){
		this.createTime = createTime;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean hasParent(){
		return this.parentId != null && this.parentId > 0;
	}

}
