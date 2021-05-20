/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.entity.User.java
 * Class:			User
 * Date:			2012-9-26
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-9-26 下午5:20:14 
 */
@Entity
@Table(name="test_user")
public class User extends IdEntity {

	/** 描述  */
	private static final long serialVersionUID = -3965896948763670517L;
	
	@Column
	private String name;

	@OneToMany(mappedBy="user", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private List<Car> cars = new ArrayList<Car>();
	
	@OneToOne(mappedBy="user", cascade={CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval=true)
	private IDCard idCard;
	
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
	 * 返回 cars 的值   
	 * @return cars  
	 */
	public List<Car> getCars() {
		return cars;
	}

	/**  
	 * 设置 cars 的值  
	 * @param cars
	 */
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	/**  
	 * 返回 idCard 的值   
	 * @return idCard  
	 */
	public IDCard getIdCard() {
		return idCard;
	}

	/**  
	 * 设置 idCard 的值  
	 * @param idCard
	 */
	public void setIdCard(IDCard idCard) {
		this.idCard = idCard;
	}
}
