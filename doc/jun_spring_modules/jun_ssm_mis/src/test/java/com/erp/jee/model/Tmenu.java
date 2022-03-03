package com.erp.jee.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tmenu entity. @author Wujun
 */
@Entity
//-----------------------------------------
//菜单权限修改
@Table(name = "JEECG_TAUTH", schema = "")
//-----------------------------------------
public class Tmenu implements java.io.Serializable {

	// Fields

	private String cid;
	private Tmenu tmenu;
	private String ciconcls;
	private String cname;
	private BigDecimal cseq;
	private String curl;
	/**权限类型 01:菜单 02:按钮*/
	private String ctype;
	private Set<Tmenu> tmenus = new HashSet<Tmenu>(0);

	// Constructors

	/** default constructor */
	public Tmenu() {
	}

	/** minimal constructor */
	public Tmenu(String cid, String cname) {
		this.cid = cid;
		this.cname = cname;
	}

	/** full constructor */
	public Tmenu(String cid, Tmenu tmenu, String ciconcls, String cname, BigDecimal cseq, String curl, Set<Tmenu> tmenus) {
		this.cid = cid;
		this.tmenu = tmenu;
		this.ciconcls = ciconcls;
		this.cname = cname;
		this.cseq = cseq;
		this.curl = curl;
		this.tmenus = tmenus;
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
	@JoinColumn(name = "CPID")
	public Tmenu getTmenu() {
		return this.tmenu;
	}

	public void setTmenu(Tmenu tmenu) {
		this.tmenu = tmenu;
	}

	@Column(name = "CICONCLS", length = 100)
	public String getCiconcls() {
		return this.ciconcls;
	}

	public void setCiconcls(String ciconcls) {
		this.ciconcls = ciconcls;
	}

	@Column(name = "CNAME", nullable = false, length = 100)
	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Column(name = "CSEQ", precision = 22, scale = 0)
	public BigDecimal getCseq() {
		return this.cseq;
	}

	public void setCseq(BigDecimal cseq) {
		this.cseq = cseq;
	}

	@Column(name = "CURL", length = 200)
	public String getCurl() {
		return this.curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tmenu")
	public Set<Tmenu> getTmenus() {
		return this.tmenus;
	}

	public void setTmenus(Set<Tmenu> tmenus) {
		this.tmenus = tmenus;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

}