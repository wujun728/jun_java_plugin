package com.erp.jee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.math.BigDecimal;
/**
 *@类:PersonEntity
 *@作者:zhangdaihao
 *@E-mail:zhangdaiscott@163.com
 *@日期:2013-1-17 11:41:26
 */

@Entity
@Table(name ="person")
@SuppressWarnings("serial")
public class PersonEntity implements java.io.Serializable {

	/**OBID*/
	private java.lang.String obid;

	/**年龄*/
	private java.lang.Integer age;

	/**出生日期*/
	private java.util.Date createdt;

	/**名字*/
	private java.lang.String name;

	/**工资*/
	private BigDecimal salary;

	/**
	 *方法: 取得obid
	 *@return: java.lang.String  obid
	 */
	@Id
	@Column(name ="OBID", nullable = false, length = 36)
	public java.lang.String getObid(){
		return this.obid;
	}

	/**
	 *方法: 设置obid
	 *@param: java.lang.String  obid
	 */
	public void setObid(java.lang.String obid){
		this.obid = obid;
	}

	/**
	 *方法: 取得age
	 *@return: java.lang.Integer  age
	 */
	@Column(name ="age")
	public java.lang.Integer getAge(){
		return this.age;
	}

	/**
	 *方法: 设置age
	 *@param: java.lang.Integer  age
	 */
	public void setAge(java.lang.Integer age){
		this.age = age;
	}

	/**
	 *方法: 取得createdt
	 *@return: java.util.Date  createdt
	 */
	@Column(name ="createdt")
	public java.util.Date getCreatedt(){
		return this.createdt;
	}

	/**
	 *方法: 设置createdt
	 *@param: java.util.Date  createdt
	 */
	public void setCreatedt(java.util.Date createdt){
		this.createdt = createdt;
	}

	/**
	 *方法: 取得name
	 *@return: java.lang.String  name
	 */
	@Column(name ="name")
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置name
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}

	/**
	 *方法: 取得salary
	 *@return: BigDecimal  salary
	 */
	@Column(name ="salary")
	public BigDecimal getSalary(){
		return this.salary;
	}

	/**
	 *方法: 设置salary
	 *@param: BigDecimal  salary
	 */
	public void setSalary(BigDecimal salary){
		this.salary = salary;
	}

}