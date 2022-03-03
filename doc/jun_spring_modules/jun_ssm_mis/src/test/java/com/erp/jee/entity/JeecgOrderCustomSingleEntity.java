package com.erp.jee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.math.BigDecimal;
/**
 *@类:JeecgOrderCustomSingleEntity
 *@作者:zhangdaihao
 *@E-mail:zhangdaiscott@163.com
 *@日期:2013-1-18 15:44:09
 */

@Entity
@Table(name ="jeecg_order_custom")
@SuppressWarnings("serial")
public class JeecgOrderCustomSingleEntity implements java.io.Serializable {

	/**OBID*/
	private java.lang.String obid;

	/**团购订单ID*/
	private java.lang.String gorderObid;

	/**团购订单号*/
	private java.lang.String goOrderCode;

	/**客户英文名*/
	private java.lang.String gocCusNameEn;

	/**姓名*/
	private java.lang.String gocCusName;

	/**人员类型*/
	private java.lang.String gocCusType;

	/**性别*/
	private java.lang.String gocSex;

	/**身份证号*/
	private java.lang.String gocIdcard;

	/**出生日期*/
	private java.util.Date gocBirthday;

	/**护照号*/
	private java.lang.String gocPassportCode;

	/**护照有效期*/
	private java.util.Date gocPassportEndDate;

	/**业务*/
	private java.lang.String gocBussContent;

	/**同住*/
	private java.lang.String gocRoomNum;

	/**备注*/
	private java.lang.String gocContent;

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
	 *方法: 取得gocCusNameEn
	 *@return: java.lang.String  gocCusNameEn
	 */
	@Column(name ="goc_cus_name_en")
	public java.lang.String getGocCusNameEn(){
		return this.gocCusNameEn;
	}

	/**
	 *方法: 设置gocCusNameEn
	 *@param: java.lang.String  gocCusNameEn
	 */
	public void setGocCusNameEn(java.lang.String gocCusNameEn){
		this.gocCusNameEn = gocCusNameEn;
	}

	/**
	 *方法: 取得gocCusName
	 *@return: java.lang.String  gocCusName
	 */
	@Column(name ="goc_cus_name")
	public java.lang.String getGocCusName(){
		return this.gocCusName;
	}

	/**
	 *方法: 设置gocCusName
	 *@param: java.lang.String  gocCusName
	 */
	public void setGocCusName(java.lang.String gocCusName){
		this.gocCusName = gocCusName;
	}

	/**
	 *方法: 取得gocCusType
	 *@return: java.lang.String  gocCusType
	 */
	@Column(name ="goc_cus_type")
	public java.lang.String getGocCusType(){
		return this.gocCusType;
	}

	/**
	 *方法: 设置gocCusType
	 *@param: java.lang.String  gocCusType
	 */
	public void setGocCusType(java.lang.String gocCusType){
		this.gocCusType = gocCusType;
	}

	/**
	 *方法: 取得gocSex
	 *@return: java.lang.String  gocSex
	 */
	@Column(name ="goc_sex")
	public java.lang.String getGocSex(){
		return this.gocSex;
	}

	/**
	 *方法: 设置gocSex
	 *@param: java.lang.String  gocSex
	 */
	public void setGocSex(java.lang.String gocSex){
		this.gocSex = gocSex;
	}

	/**
	 *方法: 取得gocIdcard
	 *@return: java.lang.String  gocIdcard
	 */
	@Column(name ="goc_idcard")
	public java.lang.String getGocIdcard(){
		return this.gocIdcard;
	}

	/**
	 *方法: 设置gocIdcard
	 *@param: java.lang.String  gocIdcard
	 */
	public void setGocIdcard(java.lang.String gocIdcard){
		this.gocIdcard = gocIdcard;
	}

	/**
	 *方法: 取得gocBirthday
	 *@return: java.util.Date  gocBirthday
	 */
	@Column(name ="goc_birthday")
	public java.util.Date getGocBirthday(){
		return this.gocBirthday;
	}

	/**
	 *方法: 设置gocBirthday
	 *@param: java.util.Date  gocBirthday
	 */
	public void setGocBirthday(java.util.Date gocBirthday){
		this.gocBirthday = gocBirthday;
	}

	/**
	 *方法: 取得gocPassportCode
	 *@return: java.lang.String  gocPassportCode
	 */
	@Column(name ="goc_passport_code")
	public java.lang.String getGocPassportCode(){
		return this.gocPassportCode;
	}

	/**
	 *方法: 设置gocPassportCode
	 *@param: java.lang.String  gocPassportCode
	 */
	public void setGocPassportCode(java.lang.String gocPassportCode){
		this.gocPassportCode = gocPassportCode;
	}

	/**
	 *方法: 取得gocPassportEndDate
	 *@return: java.util.Date  gocPassportEndDate
	 */
	@Column(name ="goc_passport_end_date")
	public java.util.Date getGocPassportEndDate(){
		return this.gocPassportEndDate;
	}

	/**
	 *方法: 设置gocPassportEndDate
	 *@param: java.util.Date  gocPassportEndDate
	 */
	public void setGocPassportEndDate(java.util.Date gocPassportEndDate){
		this.gocPassportEndDate = gocPassportEndDate;
	}

	/**
	 *方法: 取得gocBussContent
	 *@return: java.lang.String  gocBussContent
	 */
	@Column(name ="goc_buss_content")
	public java.lang.String getGocBussContent(){
		return this.gocBussContent;
	}

	/**
	 *方法: 设置gocBussContent
	 *@param: java.lang.String  gocBussContent
	 */
	public void setGocBussContent(java.lang.String gocBussContent){
		this.gocBussContent = gocBussContent;
	}

	/**
	 *方法: 取得gocRoomNum
	 *@return: java.lang.String  gocRoomNum
	 */
	@Column(name ="goc_room_num")
	public java.lang.String getGocRoomNum(){
		return this.gocRoomNum;
	}

	/**
	 *方法: 设置gocRoomNum
	 *@param: java.lang.String  gocRoomNum
	 */
	public void setGocRoomNum(java.lang.String gocRoomNum){
		this.gocRoomNum = gocRoomNum;
	}

	/**
	 *方法: 取得gocContent
	 *@return: java.lang.String  gocContent
	 */
	@Column(name ="goc_content")
	public java.lang.String getGocContent(){
		return this.gocContent;
	}

	/**
	 *方法: 设置gocContent
	 *@param: java.lang.String  gocContent
	 */
	public void setGocContent(java.lang.String gocContent){
		this.gocContent = gocContent;
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