package com.erp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.jun.plugin.utils.ExcelVO;

//import com.jun.plugin.utils.ExcelVO;

/**
 * CompanyInfo entity. @author Wujun
 */
@Entity
@Table(name = "SYS_COMPANY_INFO")
@DynamicUpdate(true)
@DynamicInsert(true)
public class CompanyInfo implements java.io.Serializable
{
	private static final long serialVersionUID = -5610203466348081933L;
	private Integer companyId;
	@ExcelVO(name = "公司名称", column = "A", isExport = true, prompt = "这是公司名称!")
	private String name;
	@ExcelVO(name = "公司电话", column = "B")
	private String tel;
	@ExcelVO(name = "公司传真", column = "C")
	private String fax;
	@ExcelVO(name = "公司地址", column = "D")
	private String address;
	@ExcelVO(name = "邮政编码", column = "E")
	private String zip;
	@ExcelVO(name = "Email", column = "F")
	private String email;
	@ExcelVO(name = "联系人", column = "G")
	private String contact;
	private String status;
	private Date created;
	private Date lastmod;
	private String manager;
	private String bank;
	private String bankaccount;
	@ExcelVO(name = "描述", column = "H")
	private String description;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public CompanyInfo()
	{
	}

	/** minimal constructor */
	public CompanyInfo(String status, Date created)
	{
		this.status = status;
		this.created = created;
	}

	/** full constructor */
	public CompanyInfo(String name, String tel, String fax, String address, String zip,
			String email, String contact, String status, Date created, Date lastmod,
			String manager, String bank, String bankaccount, String description, Integer creater,
			Integer modifyer)
	{
		this.name = name;
		this.tel = tel;
		this.fax = fax;
		this.address = address;
		this.zip = zip;
		this.email = email;
		this.contact = contact;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.manager = manager;
		this.bank = bank;
		this.bankaccount = bankaccount;
		this.description = description;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "COMPANY_ID", unique = true, nullable = false)
	public Integer getCompanyId()
	{
		return this.companyId;
	}

	public void setCompanyId(Integer companyId )
	{
		this.companyId = companyId;
	}

	@Column(name = "NAME", length = 100)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "TEL", length = 50)
	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel )
	{
		this.tel = tel;
	}

	@Column(name = "FAX", length = 50)
	public String getFax()
	{
		return this.fax;
	}

	public void setFax(String fax )
	{
		this.fax = fax;
	}

	@Column(name = "ADDRESS")
	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address )
	{
		this.address = address;
	}

	@Column(name = "ZIP", length = 100)
	public String getZip()
	{
		return this.zip;
	}

	public void setZip(String zip )
	{
		this.zip = zip;
	}

	@Column(name = "EMAIL", length = 100)
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email )
	{
		this.email = email;
	}

	@Column(name = "CONTACT", length = 100)
	public String getContact()
	{
		return this.contact;
	}

	public void setContact(String contact )
	{
		this.contact = contact;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, length = 10)
	public Date getCreated()
	{
		return this.created;
	}

	public void setCreated(Date created )
	{
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTMOD", length = 10)
	public Date getLastmod()
	{
		return this.lastmod;
	}

	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
	}

	@Column(name = "MANAGER", length = 100)
	public String getManager()
	{
		return this.manager;
	}

	public void setManager(String manager )
	{
		this.manager = manager;
	}

	@Column(name = "BANK", length = 100)
	public String getBank()
	{
		return this.bank;
	}

	public void setBank(String bank )
	{
		this.bank = bank;
	}

	@Column(name = "BANKACCOUNT", length = 100)
	public String getBankaccount()
	{
		return this.bankaccount;
	}

	public void setBankaccount(String bankaccount )
	{
		this.bankaccount = bankaccount;
	}

	@Column(name = "DESCRIPTION", length = 2000)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description )
	{
		this.description = description;
	}

	@Column(name = "CREATER")
	public Integer getCreater()
	{
		return this.creater;
	}

	public void setCreater(Integer creater )
	{
		this.creater = creater;
	}

	@Column(name = "MODIFYER")
	public Integer getModifyer()
	{
		return this.modifyer;
	}

	public void setModifyer(Integer modifyer )
	{
		this.modifyer = modifyer;
	}

}