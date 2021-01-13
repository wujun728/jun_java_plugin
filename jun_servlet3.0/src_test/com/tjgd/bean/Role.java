package com.tjgd.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * 角色 一个角色有多个权限
 * @author yeqing
 */
public class Role {
	//-------声明属性或对象------------------------
	private int id;  //角色ID
	private String roleName;  //角色名称
	private List<Auth> authList = new ArrayList<Auth>();  //获取角色的所有权限
    //-------各个属性的getXxx()和setXxx()方法-------
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Auth> getAuthList() {
		return authList;
	}
	public void setAuthList(List<Auth> authList) {
		this.authList = authList;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", authList=" + authList + "]";
	}
	
}
