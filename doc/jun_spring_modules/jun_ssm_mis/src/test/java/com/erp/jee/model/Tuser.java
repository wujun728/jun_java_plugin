package com.erp.jee.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Tuser entity. @author Wujun
 */
@Entity
@Table(name = "JEECG_TUSER", schema = "")
public class Tuser implements java.io.Serializable {

	// Fields

	private String cid;
	private String realname;
	private String subcompany;
	private String dept;
	private String mail;
	private String mobile;
	private String usertype;
	private String status;
	private Date ccreatedatetime;
	private Date cmodifydatetime;
	private String cname;
	private String cpwd;
	private Set<Tusertrole> tusertroles = new HashSet<Tusertrole>(0);

	// Constructors

	/** default constructor */
	public Tuser() {
	}

	/** minimal constructor */
	public Tuser(String cid, String cname, String cpwd) {
		this.cid = cid;
		this.cname = cname;
		this.cpwd = cpwd;
	}

	/** full constructor */
	public Tuser(String cid, Date ccreatedatetime, Date cmodifydatetime, String cname, String cpwd,String mail,String usertype, Set<Tusertrole> tusertroles) {
		this.cid = cid;
		this.ccreatedatetime = ccreatedatetime;
		this.cmodifydatetime = cmodifydatetime;
		this.cname = cname;
		this.cpwd = cpwd;
		this.mail = mail;
		this.usertype = usertype;
		this.tusertroles = tusertroles;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CCREATEDATETIME", length = 7)
	public Date getCcreatedatetime() {
		return this.ccreatedatetime;
	}

	public void setCcreatedatetime(Date ccreatedatetime) {
		this.ccreatedatetime = ccreatedatetime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CMODIFYDATETIME", length = 7)
	public Date getCmodifydatetime() {
		return this.cmodifydatetime;
	}

	public void setCmodifydatetime(Date cmodifydatetime) {
		this.cmodifydatetime = cmodifydatetime;
	}

	@Column(name = "CNAME", unique = true, nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CPWD", nullable = false, length = 100)
	public String getCpwd() {
		return this.cpwd;
	}

	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tuser")
	public Set<Tusertrole> getTusertroles() {
		return this.tusertroles;
	}

	public void setTusertroles(Set<Tusertrole> tusertroles) {
		this.tusertroles = tusertroles;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSubcompany() {
		return subcompany;
	}

	public void setSubcompany(String subcompany) {
		this.subcompany = subcompany;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}