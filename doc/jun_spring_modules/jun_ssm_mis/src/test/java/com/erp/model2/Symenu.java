package com.erp.model2;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Symenu entity. @author Wujun
 */
@Entity
@Table(name = "symenu")
public class Symenu implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023915215295440760L;
	private String id;
	private String pid;
	private String text;
	private String iconcls;
	private String src;
	private BigDecimal seq;

	// Constructors

	/** default constructor */
	public Symenu() {
	}

	/** minimal constructor */
	public Symenu(String id, String text, BigDecimal seq) {
		this.id = id;
		this.text = text;
		this.seq = seq;
	}

	/** full constructor */
	public Symenu(String id, String pid, String text, String iconcls, String src, BigDecimal seq) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.iconcls = iconcls;
		this.src = src;
		this.seq = seq;
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

	@Column(name = "PID", length = 36)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "TEXT", nullable = false, length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "ICONCLS", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "SRC", length = 200)
	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Column(name = "SEQ", nullable = false, precision = 22, scale = 0)
	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}