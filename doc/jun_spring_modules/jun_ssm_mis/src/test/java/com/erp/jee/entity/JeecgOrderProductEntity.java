package com.erp.jee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.math.BigDecimal;
/**
 *@类:JeecgOrderProductEntity
 *@作者:zhangdaihao
 *@E-mail:zhangdaiscott@163.com
 *@日期:2011-12-31 16:22:59
 */

@Entity
@Table(name ="jeecg_order_product")
@SuppressWarnings("serial")
public class JeecgOrderProductEntity implements java.io.Serializable {

	/**OBID*/
	private java.lang.String obid;

	/**团购订单ID*/
	private java.lang.String gorderObid;

	/**团购订单号*/
	private java.lang.String goOrderCode;

	/**服务项目类型*/
	private java.lang.String gopProductType;

	/**产品名称*/
	private java.lang.String gopProductName;

	/**个数*/
	private java.lang.Integer gopCount;

	/**单位*/
	private java.lang.String gopUnit;

	/**单价*/
	private BigDecimal gopOnePrice;

	/**小计*/
	private BigDecimal gopSumPrice;

	/**备注*/
	private java.lang.String gopContent;

	/**创建人*/
	private java.lang.String crtuser;

	/**创建人名字*/
	private java.lang.String crtuserName;

	/**创建时间*/
	private java.util.Date createDt;

	/**修改人*/
	private java.lang.String modifier;

	/**修改人名字*/
	private java.lang.String modifierName;

	/**修改时间*/
	private java.util.Date modifyDt;

	/**删除标记*/
	private java.lang.Integer delflag;

	/**删除时间*/
	private java.util.Date delDt;

	/**
	 *方法: 取得obid
	 *@return: java.lang.String  obid
	 */
	@Id
	@Column(name ="OBID", nullable = false, length = 36)
	public java.lang.String getObid(){
		return this.obid;
	}

	/**
	 *方法: 设置obid
	 *@param: java.lang.String  obid
	 */
	public void setObid(java.lang.String obid){
		this.obid = obid;
	}

	/**
	 *方法: 取得gorderObid
	 *@return: java.lang.String  gorderObid
	 */
	@Column(name ="gorder_obid")
	public java.lang.String getGorderObid(){
		return this.gorderObid;
	}

	/**
	 *方法: 设置gorderObid
	 *@param: java.lang.String  gorderObid
	 */
	public void setGorderObid(java.lang.String gorderObid){
		this.gorderObid = gorderObid;
	}

	/**
	 *方法: 取得goOrderCode
	 *@return: java.lang.String  goOrderCode
	 */
	@Column(name ="go_order_code")
	public java.lang.String getGoOrderCode(){
		return this.goOrderCode;
	}

	/**
	 *方法: 设置goOrderCode
	 *@param: java.lang.String  goOrderCode
	 */
	public void setGoOrderCode(java.lang.String goOrderCode){
		this.goOrderCode = goOrderCode;
	}

	/**
	 *方法: 取得gopProductType
	 *@return: java.lang.String  gopProductType
	 */
	@Column(name ="gop_product_type")
	public java.lang.String getGopProductType(){
		return this.gopProductType;
	}

	/**
	 *方法: 设置gopProductType
	 *@param: java.lang.String  gopProductType
	 */
	public void setGopProductType(java.lang.String gopProductType){
		this.gopProductType = gopProductType;
	}

	/**
	 *方法: 取得gopProductName
	 *@return: java.lang.String  gopProductName
	 */
	@Column(name ="gop_product_name")
	public java.lang.String getGopProductName(){
		return this.gopProductName;
	}

	/**
	 *方法: 设置gopProductName
	 *@param: java.lang.String  gopProductName
	 */
	public void setGopProductName(java.lang.String gopProductName){
		this.gopProductName = gopProductName;
	}

	/**
	 *方法: 取得gopCount
	 *@return: java.lang.Integer  gopCount
	 */
	@Column(name ="gop_count")
	public java.lang.Integer getGopCount(){
		return this.gopCount;
	}

	/**
	 *方法: 设置gopCount
	 *@param: java.lang.Integer  gopCount
	 */
	public void setGopCount(java.lang.Integer gopCount){
		this.gopCount = gopCount;
	}

	/**
	 *方法: 取得gopUnit
	 *@return: java.lang.String  gopUnit
	 */
	@Column(name ="gop_unit")
	public java.lang.String getGopUnit(){
		return this.gopUnit;
	}

	/**
	 *方法: 设置gopUnit
	 *@param: java.lang.String  gopUnit
	 */
	public void setGopUnit(java.lang.String gopUnit){
		this.gopUnit = gopUnit;
	}

	/**
	 *方法: 取得gopOnePrice
	 *@return: BigDecimal  gopOnePrice
	 */
	@Column(name ="gop_one_price")
	public BigDecimal getGopOnePrice(){
		return this.gopOnePrice;
	}

	/**
	 *方法: 设置gopOnePrice
	 *@param: BigDecimal  gopOnePrice
	 */
	public void setGopOnePrice(BigDecimal gopOnePrice){
		this.gopOnePrice = gopOnePrice;
	}

	/**
	 *方法: 取得gopSumPrice
	 *@return: BigDecimal  gopSumPrice
	 */
	@Column(name ="gop_sum_price")
	public BigDecimal getGopSumPrice(){
		return this.gopSumPrice;
	}

	/**
	 *方法: 设置gopSumPrice
	 *@param: BigDecimal  gopSumPrice
	 */
	public void setGopSumPrice(BigDecimal gopSumPrice){
		this.gopSumPrice = gopSumPrice;
	}

	/**
	 *方法: 取得gopContent
	 *@return: java.lang.String  gopContent
	 */
	@Column(name ="gop_content")
	public java.lang.String getGopContent(){
		return this.gopContent;
	}

	/**
	 *方法: 设置gopContent
	 *@param: java.lang.String  gopContent
	 */
	public void setGopContent(java.lang.String gopContent){
		this.gopContent = gopContent;
	}

	/**
	 *方法: 取得crtuser
	 *@return: java.lang.String  crtuser
	 */
	@Column(name ="crtuser")
	public java.lang.String getCrtuser(){
		return this.crtuser;
	}

	/**
	 *方法: 设置crtuser
	 *@param: java.lang.String  crtuser
	 */
	public void setCrtuser(java.lang.String crtuser){
		this.crtuser = crtuser;
	}

	/**
	 *方法: 取得crtuserName
	 *@return: java.lang.String  crtuserName
	 */
	@Column(name ="crtuser_name")
	public java.lang.String getCrtuserName(){
		return this.crtuserName;
	}

	/**
	 *方法: 设置crtuserName
	 *@param: java.lang.String  crtuserName
	 */
	public void setCrtuserName(java.lang.String crtuserName){
		this.crtuserName = crtuserName;
	}

	/**
	 *方法: 取得createDt
	 *@return: java.util.Date  createDt
	 */
	@Column(name ="create_dt")
	public java.util.Date getCreateDt(){
		return this.createDt;
	}

	/**
	 *方法: 设置createDt
	 *@param: java.util.Date  createDt
	 */
	public void setCreateDt(java.util.Date createDt){
		this.createDt = createDt;
	}

	/**
	 *方法: 取得modifier
	 *@return: java.lang.String  modifier
	 */
	@Column(name ="modifier")
	public java.lang.String getModifier(){
		return this.modifier;
	}

	/**
	 *方法: 设置modifier
	 *@param: java.lang.String  modifier
	 */
	public void setModifier(java.lang.String modifier){
		this.modifier = modifier;
	}

	/**
	 *方法: 取得modifierName
	 *@return: java.lang.String  modifierName
	 */
	@Column(name ="modifier_name")
	public java.lang.String getModifierName(){
		return this.modifierName;
	}

	/**
	 *方法: 设置modifierName
	 *@param: java.lang.String  modifierName
	 */
	public void setModifierName(java.lang.String modifierName){
		this.modifierName = modifierName;
	}

	/**
	 *方法: 取得modifyDt
	 *@return: java.util.Date  modifyDt
	 */
	@Column(name ="modify_dt")
	public java.util.Date getModifyDt(){
		return this.modifyDt;
	}

	/**
	 *方法: 设置modifyDt
	 *@param: java.util.Date  modifyDt
	 */
	public void setModifyDt(java.util.Date modifyDt){
		this.modifyDt = modifyDt;
	}

	/**
	 *方法: 取得delflag
	 *@return: java.lang.Integer  delflag
	 */
	@Column(name ="delflag")
	public java.lang.Integer getDelflag(){
		return this.delflag;
	}

	/**
	 *方法: 设置delflag
	 *@param: java.lang.Integer  delflag
	 */
	public void setDelflag(java.lang.Integer delflag){
		this.delflag = delflag;
	}

	/**
	 *方法: 取得delDt
	 *@return: java.util.Date  delDt
	 */
	@Column(name ="del_dt")
	public java.util.Date getDelDt(){
		return this.delDt;
	}

	/**
	 *方法: 设置delDt
	 *@param: java.util.Date  delDt
	 */
	public void setDelDt(java.util.Date delDt){
		this.delDt = delDt;
	}

}