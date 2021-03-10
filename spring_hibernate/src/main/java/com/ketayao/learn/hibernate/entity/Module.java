package com.ketayao.learn.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-8-2 下午5:36:39 
 */
@Entity
@Table(name="security_module")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Module extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -6926690440815291509L;
	
	@Column(nullable=false, length=32)
	private String name;
	
	/**
	 * 模块的排序号,越小优先级越高
	 */
	@Column(length=2)
	private Integer priority = 99;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	private Module parent;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@OrderBy("priority ASC")
	private List<Module> children = new ArrayList<Module>();

	/**  
	 * 返回 name 的值   
	 * @return name  
	 */
	public String getName() {
		return name;
	}

	/**  
	 * 设置 name 的值  
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**  
	 * 返回 priority 的值   
	 * @return priority  
	 */
	public Integer getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public Module getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(Module parent) {
		this.parent = parent;
	}

	/**  
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<Module> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<Module> children) {
		this.children = children;
	}
	
}
