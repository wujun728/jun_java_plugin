package com.jun.plugin.util.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.erp.jee.pageModel.Role;
import com.erp.jee.pageModel.User;
import com.jun.mis.entity.Resource;
/**
 * 权限验证标签类Ȩ����֤��ǩ
 * @author Wujun
 * @createTime 2011-08-31 10:53:52
 */
public class PermissionTag extends TagSupport {
	/**
	 * 权限名称Ȩ�����
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() throws JspException {
		return 0;/*
		User user = (User)pageContext.getSession().getAttribute("currentUser");
		//没有登录
		if(null == user){
			return SKIP_BODY;
		}
		//如果是超级管理员，不用验证权限
		if(user.getLoginName().equals("admin")){
			return EVAL_BODY_INCLUDE;
		}
		for(Role role : user.getRoles()){
			for(Resource resource : role.getResources()){
				if(resource.getName().equals(getName())){
					return EVAL_BODY_INCLUDE;
				}
			}
		}
		return SKIP_BODY;
	*/}
}
