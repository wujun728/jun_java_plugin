/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.entity.Car.java
 * Class:			Car
 * Date:			2012-9-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-9-26 下午5:20:26 
 */
@Entity
@Table(name="test_car")
public class Car extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -5288833035164617513L;
	
	@Column
	private String type;
	
	@Column
	private String color;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="userId")
	private User user;

	/**  
	 * 返回 type 的值   
	 * @return type  
	 */
	public String getType() {
		return type;
	}

	/**  
	 * 设置 type 的值  
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**  
	 * 返回 color 的值   
	 * @return color  
	 */
	public String getColor() {
		return color;
	}

	/**  
	 * 设置 color 的值  
	 * @param color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}
