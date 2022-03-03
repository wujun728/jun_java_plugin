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

/**
 * Customer entity. @author Wujun
 */
@Entity
@Table(name = "SYS_CUSTOMER")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Customer implements java.io.Serializable
{
	private static final long serialVersionUID = -2821343605850547602L;
	private Integer customerId;
	private String name;
	private String myid;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifiyer;
	private Integer classId;
	private String tel;
	private String fax;
	private String url;
	private String email;
	private Integer areaId;
	private String areaName;
	private Integer provinceId;
	private String provinceName;
	private Integer cityId;
	private String cityName;
	private String address;
	private String zip;
	private Integer levelId;
	private String levelName;
	private Integer priceLevel;
	private Integer contactPeriod;
	private Integer sourceId;
	private String sourceName;
	private Integer natureId;
	private String natureName;
	private Integer industryId;
	private String industryName;
	private String mainBusiness;
	private Integer sizeId;
	private String sizeName;
	private Date setupDate;
	private Integer capital;
	private String corporation;
	private Integer creditId;
	private String creditName;
	private String bank;
	private String account;
	private String taxno;
	private String shared;
	private Integer pid;
	private Integer attachmentId;
	private String description;
	private Integer saleId;
	private String saleName;
	private Integer saleOrganizationId;
	private String saleOrganizationName;
	private String customerStatus;
	private String className;
	private Integer empCount;
	private Integer tax;
	private Integer setupAccount;
	private Integer currencyId;
	private String currencyName;
	private String totalSales;
	private String deductionTax;

	// Constructors

	/** default constructor */
	public Customer()
	{
	}

	/** minimal constructor */
	public Customer(String name, String status, Date created)
	{
		this.name = name;
		this.status = status;
		this.created = created;
	}

	/** full constructor */
	public Customer(String name, String myid, String status, Date created, Date lastmod,
			Integer creater, Integer modifiyer, Integer classId, String tel, String fax,
			String url, String email, Integer areaId, String areaName, Integer provinceId,
			String provinceName, Integer cityId, String cityName, String address, String zip,
			Integer levelId, String levelName, Integer priceLevel, Integer contactPeriod,
			Integer sourceId, String sourceName, Integer natureId, String natureName,
			Integer industryId, String industryName, String mainBusiness, Integer sizeId,
			String sizeName, Date setupDate, Integer capital, String corporation, Integer creditId,
			String creditName, String bank, String account, String taxno, String shared,
			Integer pid, Integer attachmentId, String description, Integer saleId, String saleName,
			Integer saleOrganizationId, String saleOrganizationName, String customerStatus,
			String className, Integer empCount, Integer tax, Integer setupAccount,
			Integer currencyId, String currencyName, String totalSales, String deductionTax)
	{
		this.name = name;
		this.myid = myid;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifiyer = modifiyer;
		this.classId = classId;
		this.tel = tel;
		this.fax = fax;
		this.url = url;
		this.email = email;
		this.areaId = areaId;
		this.areaName = areaName;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.address = address;
		this.zip = zip;
		this.levelId = levelId;
		this.levelName = levelName;
		this.priceLevel = priceLevel;
		this.contactPeriod = contactPeriod;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.natureId = natureId;
		this.natureName = natureName;
		this.industryId = industryId;
		this.industryName = industryName;
		this.mainBusiness = mainBusiness;
		this.sizeId = sizeId;
		this.sizeName = sizeName;
		this.setupDate = setupDate;
		this.capital = capital;
		this.corporation = corporation;
		this.creditId = creditId;
		this.creditName = creditName;
		this.bank = bank;
		this.account = account;
		this.taxno = taxno;
		this.shared = shared;
		this.pid = pid;
		this.attachmentId = attachmentId;
		this.description = description;
		this.saleId = saleId;
		this.saleName = saleName;
		this.saleOrganizationId = saleOrganizationId;
		this.saleOrganizationName = saleOrganizationName;
		this.customerStatus = customerStatus;
		this.className = className;
		this.empCount = empCount;
		this.tax = tax;
		this.setupAccount = setupAccount;
		this.currencyId = currencyId;
		this.currencyName = currencyName;
		this.totalSales = totalSales;
		this.deductionTax = deductionTax;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "CUSTOMER_ID", unique = true, nullable = false)
	public Integer getCustomerId()
	{
		return this.customerId;
	}

	public void setCustomerId(Integer customerId )
	{
		this.customerId = customerId;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "MYID", length = 55)
	public String getMyid()
	{
		return this.myid;
	}

	public void setMyid(String myid )
	{
		this.myid = myid;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED", nullable = false, length = 10)
	public Date getCreated()
	{
		return this.created;
	}

	public void setCreated(Date created )
	{
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LASTMOD", length = 10)
	public Date getLastmod()
	{
		return this.lastmod;
	}

	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
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

	@Column(name = "MODIFIYER")
	public Integer getModifiyer()
	{
		return this.modifiyer;
	}

	public void setModifiyer(Integer modifiyer )
	{
		this.modifiyer = modifiyer;
	}

	@Column(name = "CLASS_ID")
	public Integer getClassId()
	{
		return this.classId;
	}

	public void setClassId(Integer classId )
	{
		this.classId = classId;
	}

	@Column(name = "TEL", length = 55)
	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel )
	{
		this.tel = tel;
	}

	@Column(name = "FAX", length = 55)
	public String getFax()
	{
		return this.fax;
	}

	public void setFax(String fax )
	{
		this.fax = fax;
	}

	@Column(name = "URL", length = 200)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url )
	{
		this.url = url;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email )
	{
		this.email = email;
	}

	@Column(name = "AREA_ID")
	public Integer getAreaId()
	{
		return this.areaId;
	}

	public void setAreaId(Integer areaId )
	{
		this.areaId = areaId;
	}

	@Column(name = "AREA_NAME", length = 100)
	public String getAreaName()
	{
		return this.areaName;
	}

	public void setAreaName(String areaName )
	{
		this.areaName = areaName;
	}

	@Column(name = "PROVINCE_ID")
	public Integer getProvinceId()
	{
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId )
	{
		this.provinceId = provinceId;
	}

	@Column(name = "PROVINCE_NAME", length = 55)
	public String getProvinceName()
	{
		return this.provinceName;
	}

	public void setProvinceName(String provinceName )
	{
		this.provinceName = provinceName;
	}

	@Column(name = "CITY_ID")
	public Integer getCityId()
	{
		return this.cityId;
	}

	public void setCityId(Integer cityId )
	{
		this.cityId = cityId;
	}

	@Column(name = "CITY_NAME", length = 55)
	public String getCityName()
	{
		return this.cityName;
	}

	public void setCityName(String cityName )
	{
		this.cityName = cityName;
	}

	@Column(name = "ADDRESS", length = 1000)
	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address )
	{
		this.address = address;
	}

	@Column(name = "ZIP", length = 20)
	public String getZip()
	{
		return this.zip;
	}

	public void setZip(String zip )
	{
		this.zip = zip;
	}

	@Column(name = "LEVEL_ID")
	public Integer getLevelId()
	{
		return this.levelId;
	}

	public void setLevelId(Integer levelId )
	{
		this.levelId = levelId;
	}

	@Column(name = "LEVEL_NAME", length = 200)
	public String getLevelName()
	{
		return this.levelName;
	}

	public void setLevelName(String levelName )
	{
		this.levelName = levelName;
	}

	@Column(name = "PRICE_LEVEL")
	public Integer getPriceLevel()
	{
		return this.priceLevel;
	}

	public void setPriceLevel(Integer priceLevel )
	{
		this.priceLevel = priceLevel;
	}

	@Column(name = "CONTACT_PERIOD")
	public Integer getContactPeriod()
	{
		return this.contactPeriod;
	}

	public void setContactPeriod(Integer contactPeriod )
	{
		this.contactPeriod = contactPeriod;
	}

	@Column(name = "SOURCE_ID")
	public Integer getSourceId()
	{
		return this.sourceId;
	}

	public void setSourceId(Integer sourceId )
	{
		this.sourceId = sourceId;
	}

	@Column(name = "SOURCE_NAME", length = 200)
	public String getSourceName()
	{
		return this.sourceName;
	}

	public void setSourceName(String sourceName )
	{
		this.sourceName = sourceName;
	}

	@Column(name = "NATURE_ID")
	public Integer getNatureId()
	{
		return this.natureId;
	}

	public void setNatureId(Integer natureId )
	{
		this.natureId = natureId;
	}

	@Column(name = "NATURE_NAME", length = 200)
	public String getNatureName()
	{
		return this.natureName;
	}

	public void setNatureName(String natureName )
	{
		this.natureName = natureName;
	}

	@Column(name = "INDUSTRY_ID")
	public Integer getIndustryId()
	{
		return this.industryId;
	}

	public void setIndustryId(Integer industryId )
	{
		this.industryId = industryId;
	}

	@Column(name = "INDUSTRY_NAME", length = 200)
	public String getIndustryName()
	{
		return this.industryName;
	}

	public void setIndustryName(String industryName )
	{
		this.industryName = industryName;
	}

	@Column(name = "MAIN_BUSINESS", length = 1000)
	public String getMainBusiness()
	{
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness )
	{
		this.mainBusiness = mainBusiness;
	}

	@Column(name = "SIZE_ID")
	public Integer getSizeId()
	{
		return this.sizeId;
	}

	public void setSizeId(Integer sizeId )
	{
		this.sizeId = sizeId;
	}

	@Column(name = "SIZE_NAME", length = 200)
	public String getSizeName()
	{
		return this.sizeName;
	}

	public void setSizeName(String sizeName )
	{
		this.sizeName = sizeName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SETUP_DATE", length = 10)
	public Date getSetupDate()
	{
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate )
	{
		this.setupDate = setupDate;
	}

	@Column(name = "CAPITAL")
	public Integer getCapital()
	{
		return this.capital;
	}

	public void setCapital(Integer capital )
	{
		this.capital = capital;
	}

	@Column(name = "CORPORATION", length = 55)
	public String getCorporation()
	{
		return this.corporation;
	}

	public void setCorporation(String corporation )
	{
		this.corporation = corporation;
	}

	@Column(name = "CREDIT_ID")
	public Integer getCreditId()
	{
		return this.creditId;
	}

	public void setCreditId(Integer creditId )
	{
		this.creditId = creditId;
	}

	@Column(name = "CREDIT_NAME", length = 55)
	public String getCreditName()
	{
		return this.creditName;
	}

	public void setCreditName(String creditName )
	{
		this.creditName = creditName;
	}

	@Column(name = "BANK", length = 200)
	public String getBank()
	{
		return this.bank;
	}

	public void setBank(String bank )
	{
		this.bank = bank;
	}

	@Column(name = "ACCOUNT", length = 55)
	public String getAccount()
	{
		return this.account;
	}

	public void setAccount(String account )
	{
		this.account = account;
	}

	@Column(name = "TAXNO", length = 100)
	public String getTaxno()
	{
		return this.taxno;
	}

	public void setTaxno(String taxno )
	{
		this.taxno = taxno;
	}

	@Column(name = "SHARED", length = 200)
	public String getShared()
	{
		return this.shared;
	}

	public void setShared(String shared )
	{
		this.shared = shared;
	}

	@Column(name = "PID")
	public Integer getPid()
	{
		return this.pid;
	}

	public void setPid(Integer pid )
	{
		this.pid = pid;
	}

	@Column(name = "ATTACHMENT_ID")
	public Integer getAttachmentId()
	{
		return this.attachmentId;
	}

	public void setAttachmentId(Integer attachmentId )
	{
		this.attachmentId = attachmentId;
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

	@Column(name = "SALE_ID")
	public Integer getSaleId()
	{
		return this.saleId;
	}

	public void setSaleId(Integer saleId )
	{
		this.saleId = saleId;
	}

	@Column(name = "SALE_NAME", length = 55)
	public String getSaleName()
	{
		return this.saleName;
	}

	public void setSaleName(String saleName )
	{
		this.saleName = saleName;
	}

	@Column(name = "SALE_ORGANIZATION_ID")
	public Integer getSaleOrganizationId()
	{
		return this.saleOrganizationId;
	}

	public void setSaleOrganizationId(Integer saleOrganizationId )
	{
		this.saleOrganizationId = saleOrganizationId;
	}

	@Column(name = "SALE_ORGANIZATION_NAME", length = 55)
	public String getSaleOrganizationName()
	{
		return this.saleOrganizationName;
	}

	public void setSaleOrganizationName(String saleOrganizationName )
	{
		this.saleOrganizationName = saleOrganizationName;
	}

	@Column(name = "CUSTOMER_STATUS", length = 1)
	public String getCustomerStatus()
	{
		return this.customerStatus;
	}

	public void setCustomerStatus(String customerStatus )
	{
		this.customerStatus = customerStatus;
	}

	@Column(name = "CLASS_NAME", length = 200)
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className )
	{
		this.className = className;
	}

	@Column(name = "EMP_COUNT")
	public Integer getEmpCount()
	{
		return this.empCount;
	}

	public void setEmpCount(Integer empCount )
	{
		this.empCount = empCount;
	}

	@Column(name = "TAX")
	public Integer getTax()
	{
		return this.tax;
	}

	public void setTax(Integer tax )
	{
		this.tax = tax;
	}

	@Column(name = "SETUP_ACCOUNT")
	public Integer getSetupAccount()
	{
		return this.setupAccount;
	}

	public void setSetupAccount(Integer setupAccount )
	{
		this.setupAccount = setupAccount;
	}

	@Column(name = "CURRENCY_ID")
	public Integer getCurrencyId()
	{
		return this.currencyId;
	}

	public void setCurrencyId(Integer currencyId )
	{
		this.currencyId = currencyId;
	}

	@Column(name = "CURRENCY_NAME", length = 55)
	public String getCurrencyName()
	{
		return this.currencyName;
	}

	public void setCurrencyName(String currencyName )
	{
		this.currencyName = currencyName;
	}

	@Column(name = "TOTAL_SALES", length = 55)
	public String getTotalSales()
	{
		return this.totalSales;
	}

	public void setTotalSales(String totalSales )
	{
		this.totalSales = totalSales;
	}

	@Column(name = "DEDUCTION_TAX", length = 1)
	public String getDeductionTax()
	{
		return this.deductionTax;
	}

	public void setDeductionTax(String deductionTax )
	{
		this.deductionTax = deductionTax;
	}

}