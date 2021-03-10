package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.web.query.PageParam;

/**
 * 菜单查询条件
 */
public class MenuQuery extends PageParam {

    @Query(name = "代码", display = true,fuzzy=true)
    private String code;
    @Query(name = "名称", display = true,fuzzy=true)
    private String name;
    
    @Query(name = "菜单入口地址", display = true,fuzzy=true)
    private String url;
  
    @Query(name="上一级菜单",display=true,type=Query.TYPE_CONTROL,control="menu")
	private Long parentMenuId;
  
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	
	
   
}
