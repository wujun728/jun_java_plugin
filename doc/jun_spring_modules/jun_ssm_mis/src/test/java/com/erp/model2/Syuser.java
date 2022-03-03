package com.erp.model2;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Syuser entity. @author Wujun
 */
@Entity
@Table(name = "syuser" , uniqueConstraints = @UniqueConstraint(columnNames = "NAME"))
public class Syuser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6647980057615957644L;
	private String id;
	private String name;
	private String password;
	private Timestamp createdatetime;
	private Timestamp modifydatetime;

	private SyuserSyrole syuserSyroles;
	// Constructors

	/** default constructor */
	public Syuser() {
	}

	/** minimal constructor */
	public Syuser(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	/** full constructor */
	public Syuser(String id, String name, String password, Timestamp createdatetime, Timestamp modifydatetime) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.createdatetime = createdatetime;
		this.modifydatetime = modifydatetime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NAME", unique = true, nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SyuserSyrole getSyuserSyroles() {
		return syuserSyroles;
	}

	public void setSyuserSyroles(SyuserSyrole syuserSyroles) {
		this.syuserSyroles = syuserSyroles;
	}

	@Column(name = "CREATEDATETIME", length = 19)
	public Timestamp getCreatedatetime() {
		return this.createdatetime;
	}

	public void setCreatedatetime(Timestamp createdatetime) {
		this.createdatetime = createdatetime;
	}

	@Column(name = "MODIFYDATETIME", length = 19)
	public Timestamp getModifydatetime() {
		return this.modifydatetime;
	}

	public void setModifydatetime(Timestamp modifydatetime) {
		this.modifydatetime = modifydatetime;
	}

}