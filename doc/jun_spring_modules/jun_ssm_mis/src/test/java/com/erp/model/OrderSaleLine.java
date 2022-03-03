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
 * OrderSaleLine entity. @author Wujun
 */
@Entity
@Table(name = "SYS_ORDER_SALE_LINE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrderSaleLine implements java.io.Serializable
{
	private static final long serialVersionUID = 2588009836993221422L;
	private Integer orderSaleLid;
	private Integer orderSaleId;
	private Integer itemId;
	private String myid;
	private String itemName;
	private String spec;
	private String unit;
	private Integer brandId;
	private String brandName;
	private Integer orderQty;
	private String description;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;
	private Double price;
	private Double tax;
	private Double priceTax;
	private Double amount;
	private Double taxAmount;
	private Double priceTaxAmount;
	private Integer taxNo;
	private Integer factQty;
	private Double discountNo;

	// Constructors

	/** default constructor */
	public OrderSaleLine()
	{
	}

	/** full constructor */
	public OrderSaleLine(Integer orderSaleId, Integer itemId, String myid, String itemName,
			String spec, String unit, Integer brandId, String brandName, Integer orderQty,
			String description, Date created, Date lastmod, String status, Integer creater,
			Integer modifyer, Double price, Double tax, Double priceTax, Double amount,
			Double taxAmount, Double priceTaxAmount, Integer taxNo, Integer factQty,
			Double discountNo)
	{
		this.orderSaleId = orderSaleId;
		this.itemId = itemId;
		this.myid = myid;
		this.itemName = itemName;
		this.spec = spec;
		this.unit = unit;
		this.brandId = brandId;
		this.brandName = brandName;
		this.orderQty = orderQty;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
		this.price = price;
		this.tax = tax;
		this.priceTax = priceTax;
		this.amount = amount;
		this.taxAmount = taxAmount;
		this.priceTaxAmount = priceTaxAmount;
		this.taxNo = taxNo;
		this.factQty = factQty;
		this.discountNo = discountNo;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ORDER_SALE_LID", unique = true, nullable = false)
	public Integer getOrderSaleLid()
	{
		return this.orderSaleLid;
	}

	public void setOrderSaleLid(Integer orderSaleLid )
	{
		this.orderSaleLid = orderSaleLid;
	}

	@Column(name = "ORDER_SALE_ID")
	public Integer getOrderSaleId()
	{
		return this.orderSaleId;
	}

	public void setOrderSaleId(Integer orderSaleId )
	{
		this.orderSaleId = orderSaleId;
	}

	@Column(name = "ITEM_ID")
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

	@Column(name = "ITEM_NAME", length = 200)
	public String getItemName()
	{
		return this.itemName;
	}

	public void setItemName(String itemName )
	{
		this.itemName = itemName;
	}

	@Column(name = "SPEC", length = 200)
	public String getSpec()
	{
		return this.spec;
	}

	public void setSpec(String spec )
	{
		this.spec = spec;
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

	@Column(name = "BRAND_ID")
	public Integer getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId(Integer brandId )
	{
		this.brandId = brandId;
	}

	@Column(name = "BRAND_NAME", length = 200)
	public String getBrandName()
	{
		return this.brandName;
	}

	public void setBrandName(String brandName )
	{
		this.brandName = brandName;
	}

	@Column(name = "ORDER_QTY")
	public Integer getOrderQty()
	{
		return this.orderQty;
	}

	public void setOrderQty(Integer orderQty )
	{
		this.orderQty = orderQty;
	}

	@Column(name = "DESCRIPTION", length = 1000)
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

	@Column(name = "PRICE", precision = 18, scale = 8)
	public Double getPrice()
	{
		return this.price;
	}

	public void setPrice(Double price )
	{
		this.price = price;
	}

	@Column(name = "TAX", precision = 18, scale = 8)
	public Double getTax()
	{
		return this.tax;
	}

	public void setTax(Double tax )
	{
		this.tax = tax;
	}

	@Column(name = "PRICE_TAX", precision = 18, scale = 8)
	public Double getPriceTax()
	{
		return this.priceTax;
	}

	public void setPriceTax(Double priceTax )
	{
		this.priceTax = priceTax;
	}

	@Column(name = "AMOUNT", precision = 18, scale = 8)
	public Double getAmount()
	{
		return this.amount;
	}

	public void setAmount(Double amount )
	{
		this.amount = amount;
	}

	@Column(name = "TAX_AMOUNT", precision = 18, scale = 8)
	public Double getTaxAmount()
	{
		return this.taxAmount;
	}

	public void setTaxAmount(Double taxAmount )
	{
		this.taxAmount = taxAmount;
	}

	@Column(name = "PRICE_TAX_AMOUNT", precision = 18, scale = 8)
	public Double getPriceTaxAmount()
	{
		return this.priceTaxAmount;
	}

	public void setPriceTaxAmount(Double priceTaxAmount )
	{
		this.priceTaxAmount = priceTaxAmount;
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

	@Column(name = "FACT_QTY")
	public Integer getFactQty()
	{
		return this.factQty;
	}

	public void setFactQty(Integer factQty )
	{
		this.factQty = factQty;
	}

	@Column(name = "DISCOUNT_NO", precision = 18, scale = 8)
	public Double getDiscountNo()
	{
		return this.discountNo;
	}

	public void setDiscountNo(Double discountNo )
	{
		this.discountNo = discountNo;
	}

}