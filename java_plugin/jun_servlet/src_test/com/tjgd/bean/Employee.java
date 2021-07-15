package com.tjgd.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * 一个用户 多个角色 
 * @author yeqing
 *
 */
public class Employee {
	//-------声明属性或对象----------------------------------------
	private int id;
	private String empName; //用户名
	private String password;  //密码
	private String position;  //职位
	private List<Role> roles = new ArrayList<Role>(0); //角色列表
	private List<Property> authList = new ArrayList<Property>(0);  //权限列表
	
	//-------添加Property，是权限类Auth的简化形式-------------------
	public void addProperty(Property pro) {
		if (pro != null) {
			for (Property oldPro : authList) {
				// 如果权限列表中已经有权限，就不再进行添加
				String oldUrl = oldPro.getUrl(); // 提交路径
				String oldAction = oldPro.getAction(); // 将要做的操作
				// 不要添加重复的权限
				if (oldUrl != null && oldAction != null
						&& oldUrl.equals(pro.getUrl())
						&& oldAction.equals(pro.getAction())) {
					return;
				}
			}
			authList.add(pro);// 如果权限列表中没有该权限，就将其加入权限列表
		}
	}
	//--------检测是否具有某一个权限---------------------------------
	public boolean checkSafe(String url, String action) {
		// authList里放的是Property对象，进行循环检查
		for (Property pro : authList) {
			if (url != null && action != null) {
				if (url.equals(pro.getUrl()) && action.equals(pro.getAction())) {
					return true;
				}
			}
		}
		return false;
	}
	//--------提供各属性或对象的getXxx()和setXxx()方法-----------------
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<Property> getAuthList() {
		return authList;
	}
	public void setAuthList(List<Property> authList) {
		this.authList = authList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + ", password=" + password + ", position=" + position
				+ ", roles=" + roles + ", authList=" + authList + "]";
	}
	
}
