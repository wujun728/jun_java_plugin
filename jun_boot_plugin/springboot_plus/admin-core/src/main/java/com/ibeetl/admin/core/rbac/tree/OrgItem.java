package com.ibeetl.admin.core.rbac.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.entity.CoreOrg;

public class OrgItem implements TreeItem  {
	private Long id;
	private CoreOrg org;
	@JsonIgnore
	private OrgItem parent;
	private List<OrgItem> children = new  ArrayList<>();
	private String name;
	public OrgItem(CoreOrg org){
		this.id = org.getId();
		this.org = org;
		this.name =org!=null?org.getName():null;
	}
	
	
	public OrgItem findChild(Long orgId){
		if(id.equals(orgId)){
			return this;
		}
		for(OrgItem item:children){
			OrgItem find = item.findChild(orgId);
			if(find!=null){
				return find;
			}
		}
		return null;
		
	}
	
	
	public OrgItem findParentOrgItem(String type){
		if(this.org.getType().equals(type)){
			return this;
		}
		OrgItem parent = this;
		while((parent=parent.getParent())!=null){
			if(parent.getId()!=0&&parent.org.getType().equals(type)){
				return parent;
			}
		}
		
		return null;
	}
	
	
	/** 查询所有符合条件的的组织机构
	 * @param type 参考SysOrg，type=0 获取所有子机构
	 * @return
	 */
	public List<OrgItem> findAllChildOrgItem(String type){
	
		return findAllChildOrgItem(type,null);
	}
	
	/**
	 * 取得当前机构的所有子机构
	 * @return
	 */
	public List<Long> findAllChildrenId(){
		List<OrgItem> items =findAllChildOrgItem(null);
		List<Long> children = new ArrayList<Long>();
		for(OrgItem item:items){
			children.add(item.getId());
		}
		return children;
	}
	
	/** 查询下级所期望的机构，但不查询breakType 指定的机构
	 * @param type  期望机构，0代表所有 ，1 集团 2 公司 等
	 * @param breakType 不希望出现的机构
	 * @return
	 */
	public List<OrgItem> findAllChildOrgItem(String type ,String breakType){
		List<OrgItem> all = new LinkedList<>();
		findChildOrgItem(all,this,type,breakType);
		return all;
	}
	
	public List<OrgItem> findAllChildOrgItem(){
		return this.findAllChildOrgItem(null, null);
	}
	
	 
	
	
	private void findChildOrgItem(List<OrgItem> all,OrgItem parent,String type,String breakType){
		for(OrgItem item:parent.children){
			if(item.org.getType().equals(breakType)){
				continue ;
			}
			
			if(type==null||type.equals(item.org.getType())){
				all.add(item);
				
			}
			
			
			findChildOrgItem(all,item,type,breakType);
		}
		
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
		OrgItem other = (OrgItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CoreOrg getOrg() {
		return org;
	}
	public void setOrg(CoreOrg org) {
		this.org = org;
	}
	public OrgItem getParent() {
		return parent;
	}
	public void setParent(OrgItem parent) {
		this.parent = parent;
		this.parent.children.add(this);
	}
	public List<OrgItem> getChildren() {
		return children;
	}
	public void setChildren(List<OrgItem> children) {
		this.children = children;
	}


	@Override
	public String getName() {
		return this.name;
	}


		
}
