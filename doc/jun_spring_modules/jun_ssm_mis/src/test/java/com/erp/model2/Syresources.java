package com.erp.model2;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Syresources entity. @author Wujun
 */
@Entity
@Table(name = "syresources")
public class Syresources implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6509136006794250010L;
	private String id;
	private String pid;
	private String text;
	private BigDecimal seq;
	private String src;
	private String descript;
	private String onoff;

	// Constructors

	/** default constructor */
	public Syresources() {
	}

	/** minimal constructor */
	public Syresources(String id, BigDecimal seq) {
		this.id = id;
		this.seq = seq;
	}

	/** full constructor */
	public Syresources(String id, String pid, String text, BigDecimal seq, String src, String descript, String onoff) {
		this.id = id;
		this.pid = pid;
		this.text = text;
		this.seq = seq;
		this.src = src;
		this.descript = descript;
		this.onoff = onoff;
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

	@Column(name = "SRC", length = 200)
	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Column(name = "DESCRIPT", length = 100)
	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Column(name = "ONOFF", length = 1)
	public String getOnoff() {
		return this.onoff;
	}

	public void setOnoff(String onoff) {
		this.onoff = onoff;
	}

}