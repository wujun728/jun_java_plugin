package com.jun.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 资源(权限)
 * @author Wujun
 * @createTime   2011-8-20 下午09:17:11
 */
public class Resource implements Serializable{
	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 资源访问地址
	 */
	private String url;
	/**
	 * 资源类型(1-菜单  0-按钮或链接)
	 */
	private int type;
	/**
	 * 资源图标
	 */
	private String icon;
	/**
	 * 父权限
	 */
	private Resource parent;
	/**
	 * 子权限
	 */
	private Set<Resource> children;
	/**
	 * 所属角色
	 */
	private Set<Role> roles;
	/**
	 * 是否是叶子节点(不做持久化)
	 */
	private boolean leaf;
	
	/**
	 * 是否包含子菜单权限(不包括按钮)
	 */
	private boolean menuLeaf;
	
	public boolean isLeaf() {
		return (null == this.getChildren() || this.getChildren().size() == 0);
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Resource(){}
	
	public Resource(String name,String url,int type,String icon,Resource parent){
		this.name = name;
		this.url = url;
		this.type = type;
		this.icon = icon;
		this.parent = parent;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Resource getParent() {
		return parent;
	}
	public void setParent(Resource parent) {
		this.parent = parent;
	}
	public Set<Resource> getChildren() {
		return children;
	}
	public void setChildren(Set<Resource> children) {
		this.children = children;
	}
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean isMenuLeaf() {
		List list = new ArrayList(this.getChildren());
		if(null == list || list.size() == 0){
			return true;
		}
		for (int i = 0; i < list.size(); i++) {
			Resource resource = (Resource)list.get(i);
			if(resource.getType() == 1){
				return false;
			}
		}
		return true;
	}

	public void setMenuLeaf(boolean menuLeaf) {
		this.menuLeaf = menuLeaf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
