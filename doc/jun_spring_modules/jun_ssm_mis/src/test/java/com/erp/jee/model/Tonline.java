package com.erp.jee.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tonline entity. @author Wujun
 */
@Entity
@Table(name = "JEECG_TONLINE", schema = "")
public class Tonline implements java.io.Serializable {

	// Fields

	private String cid;
	private Date cdatetime;
	private String cip;
	private String cname;

	// Constructors

	/** default constructor */
	public Tonline() {
	}

	/** full constructor */
	public Tonline(String cid, Date cdatetime, String cip, String cname) {
		this.cid = cid;
		this.cdatetime = cdatetime;
		this.cip = cip;
		this.cname = cname;
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
	@Column(name = "CDATETIME", nullable = false, length = 7)
	public Date getCdatetime() {
		return this.cdatetime;
	}

	public void setCdatetime(Date cdatetime) {
		this.cdatetime = cdatetime;
	}

	@Column(name = "CIP", nullable = false, length = 50)
	public String getCip() {
		return this.cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	@Column(name = "CNAME", nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}