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
 * Item entity. @author Wujun
 */
@Entity
@Table(name = "SYS_ITEM")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Item implements java.io.Serializable
{
	private static final long serialVersionUID = -5877909553299091009L;
	private Integer itemId;
	private String myid;
	private String name;
	private Integer classId;
	private String className;
	private String spec;
	private Double weight;
	private String maund;
	private String unit;
	private String packageUnit;
	private Integer converts;
	private String oldMyid;
	private Integer taxNo;
	private String isBatch;
	private String isValid;
	private Double cost;
	private Double retailPrice;
	private Double salePrice;
	private Integer brandId;
	private String brandName;
	private Integer cityId;
	private String cityName;
	private String qualityPeriod;
	private String description;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;
	private Integer attachmentId;
	private String barcode;
	private String image;

	// Constructors

	/** default constructor */
	public Item()
	{
	}

	/** full constructor */
	public Item(String myid, String name, Integer classId, String className, String spec,
			Double weight, String maund, String unit, String packageUnit, Integer converts,
			String oldMyid, Integer taxNo, String isBatch, String isValid, Double cost,
			Double retailPrice, Double salePrice, Integer brandId, String brandName, Integer cityId,
			String cityName, String qualityPeriod, String description, Date created, Date lastmod,
			String status, Integer creater, Integer modifyer, Integer attachmentId, String barcode,
			String image)
	{
		this.myid = myid;
		this.name = name;
		this.classId = classId;
		this.className = className;
		this.spec = spec;
		this.weight = weight;
		this.maund = maund;
		this.unit = unit;
		this.packageUnit = packageUnit;
		this.converts = converts;
		this.oldMyid = oldMyid;
		this.taxNo = taxNo;
		this.isBatch = isBatch;
		this.isValid = isValid;
		this.cost = cost;
		this.retailPrice = retailPrice;
		this.salePrice = salePrice;
		this.brandId = brandId;
		this.brandName = brandName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.qualityPeriod = qualityPeriod;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
		this.attachmentId = attachmentId;
		this.barcode = barcode;
		this.image = image;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID", unique = true, nullable = false)
	public Integer getItemId()
	{
		return this.itemId;
	}

	public void setItemId(Integer itemId )
	{
		this.itemId = itemId;
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

	@Column(name = "NAME")
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
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

	@Column(name = "CLASS_NAME", length = 200)
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className )
	{
		this.className = className;
	}

	@Column(name = "SPEC")
	public String getSpec()
	{
		return this.spec;
	}

	public void setSpec(String spec )
	{
		this.spec = spec;
	}

	@Column(name = "WEIGHT", precision = 10)
	public Double getWeight()
	{
		return this.weight;
	}

	public void setWeight(Double weight )
	{
		this.weight = weight;
	}

	@Column(name = "MAUND", length = 10)
	public String getMaund()
	{
		return this.maund;
	}

	public void setMaund(String maund )
	{
		this.maund = maund;
	}

	@Column(name = "UNIT", length = 55)
	public String getUnit()
	{
		return this.unit;
	}

	public void setUnit(String unit )
	{
		this.unit = unit;
	}

	@Column(name = "PACKAGE_UNIT", length = 55)
	public String getPackageUnit()
	{
		return this.packageUnit;
	}

	public void setPackageUnit(String packageUnit )
	{
		this.packageUnit = packageUnit;
	}

	@Column(name = "CONVERTS")
	public Integer getConverts()
	{
		return this.converts;
	}

	public void setConverts(Integer converts )
	{
		this.converts = converts;
	}

	@Column(name = "OLD_MYID", length = 55)
	public String getOldMyid()
	{
		return this.oldMyid;
	}

	public void setOldMyid(String oldMyid )
	{
		this.oldMyid = oldMyid;
	}

	@Column(name = "TAX_NO")
	public Integer getTaxNo()
	{
		return this.taxNo;
	}

	public void setTaxNo(Integer taxNo )
	{
		this.taxNo = taxNo;
	}

	@Column(name = "IS_BATCH", length = 1)
	public String getIsBatch()
	{
		return this.isBatch;
	}

	public void setIsBatch(String isBatch )
	{
		this.isBatch = isBatch;
	}

	@Column(name = "IS_VALID", length = 1)
	public String getIsValid()
	{
		return this.isValid;
	}

	public void setIsValid(String isValid )
	{
		this.isValid = isValid;
	}

	@Column(name = "COST", precision = 18, scale = 8)
	public Double getCost()
	{
		return this.cost;
	}

	public void setCost(Double cost )
	{
		this.cost = cost;
	}

	@Column(name = "RETAIL_PRICE", precision = 18, scale = 8)
	public Double getRetailPrice()
	{
		return this.retailPrice;
	}

	public void setRetailPrice(Double retailPrice )
	{
		this.retailPrice = retailPrice;
	}

	@Column(name = "SALE_PRICE", precision = 18, scale = 8)
	public Double getSalePrice()
	{
		return this.salePrice;
	}

	public void setSalePrice(Double salePrice )
	{
		this.salePrice = salePrice;
	}

	@Column(name = "BRAND_ID")
	public Integer getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId(Integer brandId )
	{
		this.brandId = brandId;
	}

	@Column(name = "BRAND_NAME", length = 55)
	public String getBrandName()
	{
		return this.brandName;
	}

	public void setBrandName(String brandName )
	{
		this.brandName = brandName;
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

	@Column(name = "QUALITY_PERIOD", length = 55)
	public String getQualityPeriod()
	{
		return this.qualityPeriod;
	}

	public void setQualityPeriod(String qualityPeriod )
	{
		this.qualityPeriod = qualityPeriod;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description )
	{
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED", length = 10)
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

	@Column(name = "STATUS", length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
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

	@Column(name = "ATTACHMENT_ID")
	public Integer getAttachmentId()
	{
		return this.attachmentId;
	}

	public void setAttachmentId(Integer attachmentId )
	{
		this.attachmentId = attachmentId;
	}

	@Column(name = "BARCODE", length = 55)
	public String getBarcode()
	{
		return this.barcode;
	}

	public void setBarcode(String barcode )
	{
		this.barcode = barcode;
	}

	@Column(name = "IMAGE", length = 200)
	public String getImage()
	{
		return this.image;
	}

	public void setImage(String image )
	{
		this.image = image;
	}

}