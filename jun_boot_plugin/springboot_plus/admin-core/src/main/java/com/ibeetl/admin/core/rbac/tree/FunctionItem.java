package com.ibeetl.admin.core.rbac.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.entity.CoreFunction;

public class FunctionItem  implements TreeItem{
	CoreFunction sysFunction = null;
	List<FunctionItem> children = new ArrayList<>();
	@JsonIgnore
	FunctionItem parent = null;
	String name ;
	public FunctionItem(CoreFunction sysFunction){
		this.sysFunction = sysFunction;
		this.name = sysFunction!=null?sysFunction.getName():null;
	}
	
	
	public void setParent(FunctionItem parent){
		this.parent = parent;
		parent.children.add(this);
	}
	
	public List<FunctionItem> getChildren(){
		return this.children;
	}
	
	public Long getId(){
		return sysFunction.getId();
	}
	
	public CoreFunction getData(){
		return this.sysFunction;
	}
	
	/**
	 * 查找某个指定的子功能
	 * @param functionId
	 * @return
	 */
	public FunctionItem findChild(long functionId){
		if(sysFunction.getId()==functionId){
			return this;
		}
		for(FunctionItem item:children){
			FunctionItem find = item.findChild(functionId);
			if(find!=null){
				return find;
			}
		}
		return null;
		
	}
	
	/**
	 * 查找所有的子功能
	 * @return
	 */
	public List<FunctionItem> findAllItem(){
		List<FunctionItem> all = new LinkedList<>();
		findAllChildItem(all,this);
		return all;
	}
	
	public List<Long> findAllChildrenId(){
		List<FunctionItem> items =findAllItem();
		List<Long> children = new ArrayList<Long>();
		for(FunctionItem item:items){
			children.add(item.getId());
		}
		return children;
	}
	
	
	private void findAllChildItem(List<FunctionItem> all,FunctionItem parent){
		for(FunctionItem item:parent.children){
			all.add(item);
			findAllChildItem(all,item);
		}
		
	}
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sysFunction == null) ? 0 : sysFunction.hashCode());
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
		FunctionItem other = (FunctionItem) obj;
		if (sysFunction == null) {
			if (other.sysFunction != null)
				return false;
		} else if (!sysFunction.equals(other.sysFunction))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "MenuItem [sysFunction=" + sysFunction.getName() + ","+this.children.size()+"]";
	}


	@Override
	public String getName() {
		return sysFunction==null?"":sysFunction.getName();
	}



	
	
	
	

}
