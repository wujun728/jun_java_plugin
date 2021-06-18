/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.CompanyTree.java
 * Class:			CompanyTree
 * Date:			2012-12-7
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.hibernate4.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Sort;

import com.ketayao.common.hibernate4.HibernateTree;
import com.ketayao.common.hibernate4.IdEntity;
import com.ketayao.common.hibernate4.PriorityComparator;
import com.ketayao.common.hibernate4.PriorityInterface;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-7 下午4:04:29 
 */
@Entity
@Table(name="module_tree")
public class ModuleTree extends IdEntity implements HibernateTree<Long>, PriorityInterface {

	/** 描述  */
	private static final long serialVersionUID = -6926690440815291509L;
	
	@Column
	private Integer lft;
	
	@Column
	private Integer rgt;
	
	@Column(length=32)
	private String name;
	
	@Column
	private Integer priority;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parentId")
	private ModuleTree parent;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parent")
	@Sort(comparator=PriorityComparator.class)
	private List<ModuleTree> children = new ArrayList<ModuleTree>();
	
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
	 * 返回 children 的值   
	 * @return children  
	 */
	public List<ModuleTree> getChildren() {
		return children;
	}

	/**  
	 * 设置 children 的值  
	 * @param children
	 */
	public void setChildren(List<ModuleTree> children) {
		this.children = children;
	}

	/**  
	 * 返回 parent 的值   
	 * @return parent  
	 */
	public ModuleTree getParent() {
		return parent;
	}

	/**  
	 * 设置 parent 的值  
	 * @param parent
	 */
	public void setParent(ModuleTree parent) {
		this.parent = parent;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTree#getParentId()  
	 */
	public Long getParentId() {
		ModuleTree parent = getParent();
		if (parent != null) {
			return parent.getId();
		} else {
			return null;
		}
	}
	
	/**
	 * @see HibernateTree#getLftName()
	 */
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	/**
	 * @see HibernateTree#getParentName()
	 */
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	/**
	 * @see HibernateTree#getRgtName()
	 */
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	/**
	 * Return the value associated with the column: lft
	 */
	public Integer getLft () {
		return lft;
	}

	/**
	 * Set the value related to the column: lft
	 * @param lft the lft value
	 */
	public void setLft (Integer lft) {
		this.lft = lft;
	}


	/**
	 * Return the value associated with the column: rgt
	 */
	public Integer getRgt () {
		return rgt;
	}

	/**
	 * Set the value related to the column: rgt
	 * @param rgt the rgt value
	 */
	public void setRgt (Integer rgt) {
		this.rgt = rgt;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTree#getTreeCondition()  
	 */
	public String getTreeCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.PriorityInterface#getPriority()  
	 */
	public Number getPriority() {
		return priority;
	}

	/**  
	 * 设置 priority 的值  
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
