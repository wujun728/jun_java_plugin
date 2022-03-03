package com.erp.model2;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Syrole entity. @author Wujun
 */
@Entity
@Table(name = "syrole")
public class Syrole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -388411561955695384L;
	private String id;
	private String pid;
	private String text;
	private BigDecimal seq;
	private String descript;

	// Constructors

	/** default constructor */
	public Syrole() {
	}

	/** minimal constructor */
	public Syrole(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public Syrole(String id, String pid, String text, BigDecimal seq, String descript) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.seq = seq;
		this.descript = descript;
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

	@Column(name = "TEXT", length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "SEQ", nullable = false, precision = 22, scale = 0)
	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	@Column(name = "DESCRIPT", length = 100)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}