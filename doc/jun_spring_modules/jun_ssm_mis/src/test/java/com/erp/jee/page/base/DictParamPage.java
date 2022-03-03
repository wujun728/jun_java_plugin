package com.erp.jee.page.base;

import com.erp.page.BasePage;

import java.math.BigDecimal;
/**
 *@类:DictParamPage
 *@作者:zhangdaihao
 *@E-mail:zhangdaiscott@163.com
 *@日期:2011-11-26
 */

@SuppressWarnings("serial")
public class DictParamPage extends BasePage implements java.io.Serializable {

	/**主键*/
	private java.lang.String obid;

	/**类型描述*/
	private java.lang.String paramLevelDec;

	/**类型*/
	private java.lang.String paramLevel;

	/**参数值*/
	private java.lang.String paramValue;

	/**参数名称*/
	private java.lang.String paramName;

	/**备注*/
	private java.lang.String remark;

	/**排序*/
	private java.lang.Integer paramViewOrder;

	/**删除标记*/
	private java.lang.Integer delflag;

	/**
	 *方法: 取得obid
	 *@return: java.lang.String  obid
	 */
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
	 *方法: 取得paramLevelDec
	 *@return: java.lang.String  paramLevelDec
	 */
	public java.lang.String getParamLevelDec(){
		return this.paramLevelDec;
	}

	/**
	 *方法: 设置paramLevelDec
	 *@param: java.lang.String  paramLevelDec
	 */
	public void setParamLevelDec(java.lang.String paramLevelDec){
		this.paramLevelDec = paramLevelDec;
	}

	/**
	 *方法: 取得paramLevel
	 *@return: java.lang.String  paramLevel
	 */
	public java.lang.String getParamLevel(){
		return this.paramLevel;
	}

	/**
	 *方法: 设置paramLevel
	 *@param: java.lang.String  paramLevel
	 */
	public void setParamLevel(java.lang.String paramLevel){
		this.paramLevel = paramLevel;
	}

	/**
	 *方法: 取得paramValue
	 *@return: java.lang.String  paramValue
	 */
	public java.lang.String getParamValue(){
		return this.paramValue;
	}

	/**
	 *方法: 设置paramValue
	 *@param: java.lang.String  paramValue
	 */
	public void setParamValue(java.lang.String paramValue){
		this.paramValue = paramValue;
	}

	/**
	 *方法: 取得paramName
	 *@return: java.lang.String  paramName
	 */
	public java.lang.String getParamName(){
		return this.paramName;
	}

	/**
	 *方法: 设置paramName
	 *@param: java.lang.String  paramName
	 */
	public void setParamName(java.lang.String paramName){
		this.paramName = paramName;
	}

	/**
	 *方法: 取得remark
	 *@return: java.lang.String  remark
	 */
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置remark
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	/**
	 *方法: 取得paramViewOrder
	 *@return: java.lang.Integer  paramViewOrder
	 */
	public java.lang.Integer getParamViewOrder(){
		return this.paramViewOrder;
	}

	/**
	 *方法: 设置paramViewOrder
	 *@param: java.lang.Integer  paramViewOrder
	 */
	public void setParamViewOrder(java.lang.Integer paramViewOrder){
		this.paramViewOrder = paramViewOrder;
	}

	/**
	 *方法: 取得delflag
	 *@return: java.lang.Integer  delflag
	 */
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

}