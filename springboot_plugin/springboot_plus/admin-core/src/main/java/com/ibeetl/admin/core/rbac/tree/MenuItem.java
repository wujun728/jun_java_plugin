package com.ibeetl.admin.core.rbac.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibeetl.admin.core.entity.CoreMenu;

public class MenuItem  implements TreeItem{
	CoreMenu sysMenu = null;
	List<MenuItem> children = new ArrayList<MenuItem>();
	@JsonIgnore
	MenuItem parent = null;
	String name;
	private Integer seq;
	public MenuItem(CoreMenu sysMenu){
		this.sysMenu = sysMenu;
		this.name = sysMenu!=null?sysMenu.getName():null;
		this.seq = sysMenu.getSeq();
	}
	
	
	public void setParent(MenuItem parent){
		this.parent = parent;
		parent.children.add(this);
	}
	
	public List<MenuItem> getChildren(){
		return this.children;
	}
	
	public Long getId(){
		return sysMenu.getId();
	}
	
	public CoreMenu getData(){
		return this.sysMenu;
	}
	
	
	public void filter(Set<Long> allows){
		Iterator<MenuItem> it = this.children.iterator();
		while(it.hasNext()){
			MenuItem item = it.next();
			if(item.getChildren().size()==0&&!allows.contains(item.getData().getId())){
				it.remove();
			}
			
			if(item.getChildren().size()!=0){
				item.filter(allows);
				if(item.getChildren().size()==0){
					it.remove();
				}
			}
			
			
		
		}
		
	}
	
	
	/**
	 * 查找某个指定的子功能
	 * @param functionId
	 * @return
	 */
	public MenuItem findChild(Long menuId){
		if(sysMenu.getId().equals(menuId)){
			return this;
		}
		for(MenuItem item:children){
			MenuItem find = item.findChild(menuId);
			if(find!=null){
				return find;
			}
		}
		return null;
		
	}
	
	/**
	 * 查找所有的子菜单
	 * @return
	 */
	public List<MenuItem> findAllItem(){
		List<MenuItem> all = new LinkedList<>();
		findAllChildItem(all,this);
		return all;
	}
	
	
	private void findAllChildItem(List<MenuItem> all,MenuItem parent){
		for(MenuItem item:parent.children){
			all.add(item);
			findAllChildItem(all,item);
		}
		
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sysMenu == null) ? 0 : sysMenu.hashCode());
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
		MenuItem other = (MenuItem) obj;
		if (sysMenu == null) {
			if (other.sysMenu != null)
				return false;
		} else if (!sysMenu.equals(other.sysMenu))
			return false;
		return true;
	}


	public MenuItem getParent() {
		return parent;
	}


	@Override
	public String toString() {
		return "MenuItem [sysMenu=" + sysMenu.getName() + ","+this.children.size()+"]";
	}


	@Override
	public String getName() {
		return this.name;
	}


	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}



	
	
	
	

}
