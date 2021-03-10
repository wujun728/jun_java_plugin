package com.ketayao.learn.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="monkeys")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Monkey {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=64)
	private String name;
	
	@Column
	private int age;
	
	@Column
	private char gender;

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

	/**  
	 * 返回 age 的值   
	 * @return age  
	 */
	public int getAge() {
		return age;
	}

	/**  
	 * 设置 age 的值  
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**  
	 * 返回 gender 的值   
	 * @return gender  
	 */
	public char getGender() {
		return gender;
	}

	/**  
	 * 设置 gender 的值  
	 * @param gender
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	

}
