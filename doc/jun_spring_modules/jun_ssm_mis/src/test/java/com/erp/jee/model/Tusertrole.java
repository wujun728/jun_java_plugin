package com.erp.jee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tusertrole entity. @author Wujun
 */
@Entity
@Table(name = "JEECG_TUSERTROLE", schema = "")
public class Tusertrole implements java.io.Serializable {

	// Fields

	private String cid;
	private Trole trole;
	private Tuser tuser;

	// Constructors

	/** default constructor */
	public Tusertrole() {
	}

	/** minimal constructor */
	public Tusertrole(String cid) {
		this.cid = cid;
	}

	/** full constructor */
	public Tusertrole(String cid, Trole trole, Tuser tuser) {
		this.cid = cid;
		this.trole = trole;
		this.tuser = tuser;
	}

	// Property accessors
	@Id
	@Column(name = "CID",  nullable = false, length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CROLEID")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSERID")
	public Tuser getTuser() {
		return this.tuser;
	}

	public void setTuser(Tuser tuser) {
		this.tuser = tuser;
	}

}