package com.erp.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SyuserSyrole entity. @author Wujun
 */
@Entity
@Table(name = "syuser_syrole" )
public class SyuserSyrole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1282818040718700882L;
	private String id;
	private String syroleId;
	private String syuserId;
	private Syuser syuser;

	// Constructors

	public Syuser getSyuser() {
		return syuser;
	}

	public void setSyuser(Syuser syuser) {
		this.syuser = syuser;
	}

	/** default constructor */
	public SyuserSyrole() {
	}

	/** full constructor */
	public SyuserSyrole(String id, String syroleId, String syuserId) {
		this.id = id;
		this.syroleId = syroleId;
		this.syuserId = syuserId;
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

	@Column(name = "SYROLE_ID", nullable = false, length = 36)
	public String getSyroleId() {
		return this.syroleId;
	}

	public void setSyroleId(String syroleId) {
		this.syroleId = syroleId;
	}

	@Column(name = "SYUSER_ID", nullable = false, length = 36)
	public String getSyuserId() {
		return this.syuserId;
	}

	public void setSyuserId(String syuserId) {
		this.syuserId = syuserId;
	}

}