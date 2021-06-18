/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.entity.AssignKey.java
 * Class:			AssignKey
 * Date:			2012-9-27
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-9-27 上午10:55:05 
 */
@Entity
@Table(name="test_assign_key")
public class AssignKey {
	@Id
	private Long id;
	
	@Column
	private String name;

	/**  
	 * 返回 id 的值   
	 * @return id  
	 */
	public Long getId() {
		return id;
	}

	/**  
	 * 设置 id 的值  
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

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
	
}
