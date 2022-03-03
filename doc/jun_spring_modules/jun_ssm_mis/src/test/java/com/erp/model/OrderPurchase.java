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
 * OrderPurchase entity. @author Wujun
 */
@Entity
@Table(name = "SYS_ORDER_PURCHASE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrderPurchase implements java.io.Serializable
{
	private static final long serialVersionUID = -3587337822361445191L;
	private Integer orderPurchaseId;
	private String myid;
	private Date setupDate;
	private Integer warehouseId;
	private Integer suplierId;
	private String suplierName;
	private String suplierMyid;
	private Integer projectId;
	private String projectName;
	private String suplierAddress;
	private String suplierContact;
	private String suplierTel;
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
	private Integer buyerId;
	private String buyerName;
	private Integer buyerOrganizationId;
	private String buyerOrganizationName;
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
	private String suplierOrderNo;
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
	public OrderPurchase()
	{
	}

	/** full constructor */
	public OrderPurchase(String myid, Date setupDate, Integer warehouseId, Integer suplierId,
			String suplierName, String suplierMyid, Integer projectId, String projectName,
			String suplierAddress, String suplierContact, String suplierTel, String contactMobile,
			String contactTel, String contactFax, Integer deliveryMode, String deliveryModeName,
			Date estimatedDelivery, String deliveryAddress, String orderDesc, Integer payMode,
			Integer isinvoice, Integer buyerId, String buyerName, Integer buyerOrganizationId,
			String buyerOrganizationName, String warehouseName, Integer enterId, String enterName,
			Integer enterOrganizationId, String enterOrganizationName, Date enterDate,
			Integer auditeId, String auditeName, Integer auditeOrganizationId,
			String auditeOrganizationName, Date auditeDate, String auditeStatus,
			Integer attachmentId, Integer sourceObject, Integer objectId, Integer printCount,
			Integer classId, String className, Integer batchId, String batchNo,
			String deductionTax, String suplierOrderNo, Double amount, Double taxAmount,
			Double totalAmount, Double advancePayment, Date created, Date lastmod, String status,
			Integer creater, Integer modifyer, String orderStatus, Integer currencyId,
			String currencyName)
	{
		this.myid = myid;
		this.setupDate = setupDate;
		this.warehouseId = warehouseId;
		this.suplierId = suplierId;
		this.suplierName = suplierName;
		this.suplierMyid = suplierMyid;
		this.projectId = projectId;
		this.projectName = projectName;
		this.suplierAddress = suplierAddress;
		this.suplierContact = suplierContact;
		this.suplierTel = suplierTel;
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
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerOrganizationId = buyerOrganizationId;
		this.buyerOrganizationName = buyerOrganizationName;
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
		this.suplierOrderNo = suplierOrderNo;
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
	@Column(name = "ORDER_PURCHASE_ID", unique = true, nullable = false)
	public Integer getOrderPurchaseId()
	{
		return this.orderPurchaseId;
	}

	public void setOrderPurchaseId(Integer orderPurchaseId )
	{
		this.orderPurchaseId = orderPurchaseId;
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

	@Column(name = "WAREHOUSE_ID")
	public Integer getWarehouseId()
	{
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId )
	{
		this.warehouseId = warehouseId;
	}

	@Column(name = "SUPLIER_ID")
	public Integer getSuplierId()
	{
		return this.suplierId;
	}

	public void setSuplierId(Integer suplierId )
	{
		this.suplierId = suplierId;
	}

	@Column(name = "SUPLIER_NAME", length = 200)
	public String getSuplierName()
	{
		return this.suplierName;
	}

	public void setSuplierName(String suplierName )
	{
		this.suplierName = suplierName;
	}

	@Column(name = "SUPLIER_MYID", length = 55)
	public String getSuplierMyid()
	{
		return this.suplierMyid;
	}

	public void setSuplierMyid(String suplierMyid )
	{
		this.suplierMyid = suplierMyid;
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

	@Column(name = "SUPLIER_ADDRESS", length = 200)
	public String getSuplierAddress()
	{
		return this.suplierAddress;
	}

	public void setSuplierAddress(String suplierAddress )
	{
		this.suplierAddress = suplierAddress;
	}

	@Column(name = "SUPLIER_CONTACT", length = 55)
	public String getSuplierContact()
	{
		return this.suplierContact;
	}

	public void setSuplierContact(String suplierContact )
	{
		this.suplierContact = suplierContact;
	}

	@Column(name = "SUPLIER_TEL", length = 55)
	public String getSuplierTel()
	{
		return this.suplierTel;
	}

	public void setSuplierTel(String suplierTel )
	{
		this.suplierTel = suplierTel;
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

	@Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "BUYER_ID")
	public Integer getBuyerId()
	{
		return this.buyerId;
	}

	public void setBuyerId(Integer buyerId )
	{
		this.buyerId = buyerId;
	}

	@Column(name = "BUYER_NAME", length = 55)
	public String getBuyerName()
	{
		return this.buyerName;
	}

	public void setBuyerName(String buyerName )
	{
		this.buyerName = buyerName;
	}

	@Column(name = "BUYER_ORGANIZATION_ID")
	public Integer getBuyerOrganizationId()
	{
		return this.buyerOrganizationId;
	}

	public void setBuyerOrganizationId(Integer buyerOrganizationId )
	{
		this.buyerOrganizationId = buyerOrganizationId;
	}

	@Column(name = "BUYER_ORGANIZATION_NAME", length = 55)
	public String getBuyerOrganizationName()
	{
		return this.buyerOrganizationName;
	}

	public void setBuyerOrganizationName(String buyerOrganizationName )
	{
		this.buyerOrganizationName = buyerOrganizationName;
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

	@Temporal(TemporalType.TIMESTAMP)
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

	@Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "SUPLIER_ORDER_NO", length = 100)
	public String getSuplierOrderNo()
	{
		return this.suplierOrderNo;
	}

	public void setSuplierOrderNo(String suplierOrderNo )
	{
		this.suplierOrderNo = suplierOrderNo;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", length = 10)
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