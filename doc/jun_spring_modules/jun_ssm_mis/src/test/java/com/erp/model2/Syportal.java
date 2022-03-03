package com.erp.model2;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Syportal entity. @author Wujun
 */
@Entity
@Table(name = "syportal")
public class Syportal implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2975263924551839012L;
	private String id;
	private String title;
	private String src;
	private BigDecimal height;
	private String closable;
	private String collapsible;
	private BigDecimal seq;

	// Constructors

	/** default constructor */
	public Syportal() {
	}

	/** minimal constructor */
	public Syportal(String id, String title, String src) {
		this.id = id;
		this.title = title;
		this.src = src;
	}

	/** full constructor */
	public Syportal(String id, String title, String src, BigDecimal height, String closable, String collapsible, BigDecimal seq) {
		this.id = id;
		this.title = title;
		this.src = src;
		this.height = height;
		this.closable = closable;
		this.collapsible = collapsible;
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

	@Column(name = "TITLE", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "SRC", nullable = false, length = 200)
	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Column(name = "HEIGHT", precision = 22, scale = 0)
	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	@Column(name = "CLOSABLE", length = 1)
	public String getClosable() {
		return this.closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	@Column(name = "COLLAPSIBLE", length = 1)
	public String getCollapsible() {
		return this.collapsible;
	}

	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	@Column(name = "SEQ", precision = 22, scale = 0)
	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}