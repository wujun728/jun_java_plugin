package com.erp.jee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Troletauth entity. @author Wujun
 */
@Entity
@Table(name = "JEECG_TROLETAUTH", schema = "")
public class Troletauth implements java.io.Serializable {

	// Fields

	private String cid;
	private Tauth tauth;
	private Trole trole;

	// Constructors

	/** default constructor */
	public Troletauth() {
	}

	/** minimal constructor */
	public Troletauth(String cid) {
		this.cid = cid;
	}

	/** full constructor */
	public Troletauth(String cid, Tauth tauth, Trole trole) {
		this.cid = cid;
		this.tauth = tauth;
		this.trole = trole;
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
	@JoinColumn(name = "CAUTHID")
	public Tauth getTauth() {
		return this.tauth;
	}

	public void setTauth(Tauth tauth) {
		this.tauth = tauth;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CROLEID")
	public Trole getTrole() {
		return this.trole;
	}

	public void setTrole(Trole trole) {
		this.trole = trole;
	}

}