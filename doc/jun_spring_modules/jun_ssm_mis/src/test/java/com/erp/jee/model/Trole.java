package com.erp.jee.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Trole entity. @author Wujun
 */
@Entity
@Table(name = "JEECG_TROLE", schema = "")
public class Trole implements java.io.Serializable {

	// Fields

	private String cid;
	private String cdesc;
	private String cname;
	private Set<Troletauth> troletauths = new HashSet<Troletauth>(0);
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	// Constructors

	/** default constructor */
	public Trole() {
	}

	/** minimal constructor */
	public Trole(String cid, String cname) {
		this.cid = cid;
		this.cname = cname;
	}

	/** full constructor */
	public Trole(String cid, String cdesc, String cname, Set<Troletauth> troletauths, Set<Tusertrole> tusertroles) {
		this.cid = cid;
		this.cdesc = cdesc;
		this.cname = cname;
		this.troletauths = troletauths;
		this.tusertroles = tusertroles;
	}

	// Property accessors
	@Id
	@Column(name = "CID", nullable = false, length = 36)
	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "CDESC", length = 200)
	public String getCdesc() {
		return this.cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	@Column(name = "CNAME", nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Troletauth> getTroletauths() {
		return this.troletauths;
	}

	public void setTroletauths(Set<Troletauth> troletauths) {
		this.troletauths = troletauths;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trole")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}

}