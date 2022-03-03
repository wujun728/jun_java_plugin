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
 * OrderSale entity. @author Wujun
 */
@Entity
@Table(name = "SYS_ORDER_SALE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrderSale implements java.io.Serializable
{
	private static final long serialVersionUID = 2165814343170720640L;
	private Integer orderSaleId;
	private String myid;
	private Date setupDate;
	private Integer setupAccount;
	private Integer warehouseId;
	private Integer customerId;
	private String customerName;
	private String customerMyid;
	private Integer projectId;
	private String projectName;
	private String customerAddress;
	private String customerContact;
	private String customerTel;
	private String contactMobile;
	private String contactTel;
	private String contactFax;
	private Integer deliveryMode;
	private String deliveryModeName;
	private Date estimatedDelivery;
	private String deliveryAddress;
	private String orderDesc;
	private Integer payMode;
	private Integer isinvoice;
	private Integer saleId;
	private String saleName;
	private Integer saleOrganizationId;
	private String saleOrganizationName;
	private String warehouseName;
	private Integer enterId;
	private String enterName;
	private Integer enterOrganizationId;
	private String enterOrganizationName;
	private Date enterDate;
	private Integer auditeId;
	private String auditeName;
	private Integer auditeOrganizationId;
	private String auditeOrganizationName;
	private Date auditeDate;
	private String auditeStatus;
	private Integer attachmentId;
	private Integer sourceObject;
	private Integer objectId;
	private Integer printCount;
	private Integer classId;
	private String className;
	private Integer batchId;
	private String batchNo;
	private String deductionTax;
	private String customerOrderNo;
	private Double amount;
	private Double taxAmount;
	private Double totalAmount;
	private Double advancePayment;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;
	private String orderStatus;
	private Integer currencyId;
	private String currencyName;

	// Constructors

	/** default constructor */
	public OrderSale()
	{
	}

	/** full constructor */
	public OrderSale(String myid, Date setupDate, Integer setupAccount, Integer warehouseId,
			Integer customerId, String customerName, String customerMyid, Integer projectId,
			String projectName, String customerAddress, String customerContact, String customerTel,
			String contactMobile, String contactTel, String contactFax, Integer deliveryMode,
			String deliveryModeName, Date estimatedDelivery, String deliveryAddress,
			String orderDesc, Integer payMode, Integer isinvoice, Integer saleId, String saleName,
			Integer saleOrganizationId, String saleOrganizationName, String warehouseName,
			Integer enterId, String enterName, Integer enterOrganizationId,
			String enterOrganizationName, Date enterDate, Integer auditeId, String auditeName,
			Integer auditeOrganizationId, String auditeOrganizationName, Date auditeDate,
			String auditeStatus, Integer attachmentId, Integer sourceObject, Integer objectId,
			Integer printCount, Integer classId, String className, Integer batchId, String batchNo,
			String deductionTax, String customerOrderNo, Double amount, Double taxAmount,
			Double totalAmount, Double advancePayment, Date created, Date lastmod, String status,
			Integer creater, Integer modifyer, String orderStatus, Integer currencyId,
			String currencyName)
	{
		this.myid = myid;
		this.setupDate = setupDate;
		this.setupAccount = setupAccount;
		this.warehouseId = warehouseId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerMyid = customerMyid;
		this.projectId = projectId;
		this.projectName = projectName;
		this.customerAddress = customerAddress;
		this.customerContact = customerContact;
		this.customerTel = customerTel;
		this.contactMobile = contactMobile;
		this.contactTel = contactTel;
		this.contactFax = contactFax;
		this.deliveryMode = deliveryMode;
		this.deliveryModeName = deliveryModeName;
		this.estimatedDelivery = estimatedDelivery;
		this.deliveryAddress = deliveryAddress;
		this.orderDesc = orderDesc;
		this.payMode = payMode;
		this.isinvoice = isinvoice;
		this.saleId = saleId;
		this.saleName = saleName;
		this.saleOrganizationId = saleOrganizationId;
		this.saleOrganizationName = saleOrganizationName;
		this.warehouseName = warehouseName;
		this.enterId = enterId;
		this.enterName = enterName;
		this.enterOrganizationId = enterOrganizationId;
		this.enterOrganizationName = enterOrganizationName;
		this.enterDate = enterDate;
		this.auditeId = auditeId;
		this.auditeName = auditeName;
		this.auditeOrganizationId = auditeOrganizationId;
		this.auditeOrganizationName = auditeOrganizationName;
		this.auditeDate = auditeDate;
		this.auditeStatus = auditeStatus;
		this.attachmentId = attachmentId;
		this.sourceObject = sourceObject;
		this.objectId = objectId;
		this.printCount = printCount;
		this.classId = classId;
		this.className = className;
		this.batchId = batchId;
		this.batchNo = batchNo;
		this.deductionTax = deductionTax;
		this.customerOrderNo = customerOrderNo;
		this.amount = amount;
		this.taxAmount = taxAmount;
		this.totalAmount = totalAmount;
		this.advancePayment = advancePayment;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
		this.orderStatus = orderStatus;
		this.currencyId = currencyId;
		this.currencyName = currencyName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ORDER_SALE_ID", unique = true, nullable = false)
	public Integer getOrderSaleId()
	{
		return this.orderSaleId;
	}

	public void setOrderSaleId(Integer orderSaleId )
	{
		this.orderSaleId = orderSaleId;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "SETUP_DATE", length = 10)
	public Date getSetupDate()
	{
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate )
	{
		this.setupDate = setupDate;
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

	@Column(name = "WAREHOUSE_ID")
	public Integer getWarehouseId()
	{
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId )
	{
		this.warehouseId = warehouseId;
	}

	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId()
	{
		return this.customerId;
	}

	public void setCustomerId(Integer customerId )
	{
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName()
	{
		return this.customerName;
	}

	public void setCustomerName(String customerName )
	{
		this.customerName = customerName;
	}

	@Column(name = "CUSTOMER_MYID", length = 55)
	public String getCustomerMyid()
	{
		return this.customerMyid;
	}

	public void setCustomerMyid(String customerMyid )
	{
		this.customerMyid = customerMyid;
	}

	@Column(name = "PROJECT_ID")
	public Integer getProjectId()
	{
		return this.projectId;
	}

	public void setProjectId(Integer projectId )
	{
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 200)
	public String getProjectName()
	{
		return this.projectName;
	}

	public void setProjectName(String projectName )
	{
		this.projectName = projectName;
	}

	@Column(name = "CUSTOMER_ADDRESS", length = 200)
	public String getCustomerAddress()
	{
		return this.customerAddress;
	}

	public void setCustomerAddress(String customerAddress )
	{
		this.customerAddress = customerAddress;
	}

	@Column(name = "CUSTOMER_CONTACT", length = 55)
	public String getCustomerContact()
	{
		return this.customerContact;
	}

	public void setCustomerContact(String customerContact )
	{
		this.customerContact = customerContact;
	}

	@Column(name = "CUSTOMER_TEL", length = 55)
	public String getCustomerTel()
	{
		return this.customerTel;
	}

	public void setCustomerTel(String customerTel )
	{
		this.customerTel = customerTel;
	}

	@Column(name = "CONTACT_MOBILE", length = 20)
	public String getContactMobile()
	{
		return this.contactMobile;
	}

	public void setContactMobile(String contactMobile )
	{
		this.contactMobile = contactMobile;
	}

	@Column(name = "CONTACT_TEL", length = 20)
	public String getContactTel()
	{
		return this.contactTel;
	}

	public void setContactTel(String contactTel )
	{
		this.contactTel = contactTel;
	}

	@Column(name = "CONTACT_FAX", length = 20)
	public String getContactFax()
	{
		return this.contactFax;
	}

	public void setContactFax(String contactFax )
	{
		this.contactFax = contactFax;
	}

	@Column(name = "DELIVERY_MODE")
	public Integer getDeliveryMode()
	{
		return this.deliveryMode;
	}

	public void setDeliveryMode(Integer deliveryMode )
	{
		this.deliveryMode = deliveryMode;
	}

	@Column(name = "DELIVERY_MODE_NAME", length = 55)
	public String getDeliveryModeName()
	{
		return this.deliveryModeName;
	}

	public void setDeliveryModeName(String deliveryModeName )
	{
		this.deliveryModeName = deliveryModeName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ESTIMATED_DELIVERY", length = 10)
	public Date getEstimatedDelivery()
	{
		return this.estimatedDelivery;
	}

	public void setEstimatedDelivery(Date estimatedDelivery )
	{
		this.estimatedDelivery = estimatedDelivery;
	}

	@Column(name = "DELIVERY_ADDRESS", length = 300)
	public String getDeliveryAddress()
	{
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress )
	{
		this.deliveryAddress = deliveryAddress;
	}

	@Column(name = "ORDER_DESC", length = 1000)
	public String getOrderDesc()
	{
		return this.orderDesc;
	}

	public void setOrderDesc(String orderDesc )
	{
		this.orderDesc = orderDesc;
	}

	@Column(name = "PAY_MODE")
	public Integer getPayMode()
	{
		return this.payMode;
	}

	public void setPayMode(Integer payMode )
	{
		this.payMode = payMode;
	}

	@Column(name = "ISINVOICE")
	public Integer getIsinvoice()
	{
		return this.isinvoice;
	}

	public void setIsinvoice(Integer isinvoice )
	{
		this.isinvoice = isinvoice;
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

	@Column(name = "WAREHOUSE_NAME", length = 200)
	public String getWarehouseName()
	{
		return this.warehouseName;
	}

	public void setWarehouseName(String warehouseName )
	{
		this.warehouseName = warehouseName;
	}

	@Column(name = "ENTER_ID")
	public Integer getEnterId()
	{
		return this.enterId;
	}

	public void setEnterId(Integer enterId )
	{
		this.enterId = enterId;
	}

	@Column(name = "ENTER_NAME", length = 55)
	public String getEnterName()
	{
		return this.enterName;
	}

	public void setEnterName(String enterName )
	{
		this.enterName = enterName;
	}

	@Column(name = "ENTER_ORGANIZATION_ID")
	public Integer getEnterOrganizationId()
	{
		return this.enterOrganizationId;
	}

	public void setEnterOrganizationId(Integer enterOrganizationId )
	{
		this.enterOrganizationId = enterOrganizationId;
	}

	@Column(name = "ENTER_ORGANIZATION_NAME", length = 55)
	public String getEnterOrganizationName()
	{
		return this.enterOrganizationName;
	}

	public void setEnterOrganizationName(String enterOrganizationName )
	{
		this.enterOrganizationName = enterOrganizationName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENTER_DATE", length = 10)
	public Date getEnterDate()
	{
		return this.enterDate;
	}

	public void setEnterDate(Date enterDate )
	{
		this.enterDate = enterDate;
	}

	@Column(name = "AUDITE_ID")
	public Integer getAuditeId()
	{
		return this.auditeId;
	}

	public void setAuditeId(Integer auditeId )
	{
		this.auditeId = auditeId;
	}

	@Column(name = "AUDITE_NAME", length = 55)
	public String getAuditeName()
	{
		return this.auditeName;
	}

	public void setAuditeName(String auditeName )
	{
		this.auditeName = auditeName;
	}

	@Column(name = "AUDITE_ORGANIZATION_ID")
	public Integer getAuditeOrganizationId()
	{
		return this.auditeOrganizationId;
	}

	public void setAuditeOrganizationId(Integer auditeOrganizationId )
	{
		this.auditeOrganizationId = auditeOrganizationId;
	}

	@Column(name = "AUDITE_ORGANIZATION_NAME", length = 55)
	public String getAuditeOrganizationName()
	{
		return this.auditeOrganizationName;
	}

	public void setAuditeOrganizationName(String auditeOrganizationName )
	{
		this.auditeOrganizationName = auditeOrganizationName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "AUDITE_DATE", length = 10)
	public Date getAuditeDate()
	{
		return this.auditeDate;
	}

	public void setAuditeDate(Date auditeDate )
	{
		this.auditeDate = auditeDate;
	}

	@Column(name = "AUDITE_STATUS", length = 1)
	public String getAuditeStatus()
	{
		return this.auditeStatus;
	}

	public void setAuditeStatus(String auditeStatus )
	{
		this.auditeStatus = auditeStatus;
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

	@Column(name = "SOURCE_OBJECT")
	public Integer getSourceObject()
	{
		return this.sourceObject;
	}

	public void setSourceObject(Integer sourceObject )
	{
		this.sourceObject = sourceObject;
	}

	@Column(name = "OBJECT_ID")
	public Integer getObjectId()
	{
		return this.objectId;
	}

	public void setObjectId(Integer objectId )
	{
		this.objectId = objectId;
	}

	@Column(name = "PRINT_COUNT")
	public Integer getPrintCount()
	{
		return this.printCount;
	}

	public void setPrintCount(Integer printCount )
	{
		this.printCount = printCount;
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

	@Column(name = "CLASS_NAME", length = 55)
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className )
	{
		this.className = className;
	}

	@Column(name = "BATCH_ID")
	public Integer getBatchId()
	{
		return this.batchId;
	}

	public void setBatchId(Integer batchId )
	{
		this.batchId = batchId;
	}

	@Column(name = "BATCH_NO")
	public String getBatchNo()
	{
		return this.batchNo;
	}

	public void setBatchNo(String batchNo )
	{
		this.batchNo = batchNo;
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

	@Column(name = "CUSTOMER_ORDER_NO", length = 100)
	public String getCustomerOrderNo()
	{
		return this.customerOrderNo;
	}

	public void setCustomerOrderNo(String customerOrderNo )
	{
		this.customerOrderNo = customerOrderNo;
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

	@Column(name = "TOTAL_AMOUNT", precision = 18, scale = 8)
	public Double getTotalAmount()
	{
		return this.totalAmount;
	}

	public void setTotalAmount(Double totalAmount )
	{
		this.totalAmount = totalAmount;
	}

	@Column(name = "ADVANCE_PAYMENT", precision = 18, scale = 8)
	public Double getAdvancePayment()
	{
		return this.advancePayment;
	}

	public void setAdvancePayment(Double advancePayment )
	{
		this.advancePayment = advancePayment;
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

	@Column(name = "ORDER_STATUS", length = 1)
	public String getOrderStatus()
	{
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus )
	{
		this.orderStatus = orderStatus;
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

}