package com.ibeetl.admin.console.web.query;

import java.util.List;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.web.query.PageParam;

/**
 * 功能表单查询条件
 */
public class FunctionQuery extends PageParam {

    @Query(name = "代码", display = true,fuzzy=true)
    private String code;
    @Query(name = "名称", display = true,fuzzy=true)
    private String name;
    @Query(name = "访问地址",fuzzy=true)
    private String accessUrl;
    
	@Query(name="上一级功能",display=true,type=Query.TYPE_CONTROL,control="fun")
	private Long parentFunctionId;
    
    private Long functionId;
    //查询子类
    private List<Long> functionIds; 
    
    
    
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
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	public List<Long> getFunctionIds() {
		return functionIds;
	}
	public void setFunctionIds(List<Long> functionIds) {
		this.functionIds = functionIds;
	}
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public Long getParentFunctionId() {
		return parentFunctionId;
	}
	public void setParentFunctionId(Long parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}
	
    
   
}
