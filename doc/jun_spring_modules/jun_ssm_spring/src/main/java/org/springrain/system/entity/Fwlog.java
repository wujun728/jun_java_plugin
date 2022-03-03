package org.springrain.system.entity;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.springrain.frame.annotation.NotLog;
import org.springrain.frame.annotation.TableSuffix;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
import org.springrain.frame.util.GlobalStatic;
/**
 * TODO 在此加入类描述
 * @copyright {@link springrain}
 * @author weicms.net<Auto generate>
 * @version  2013-07-29 11:36:44
 * @see org.springrain.system.entity.Fwlog
 */
@Table(name="t_fwlog")
@TableSuffix(name="ext")
@NotLog
public class Fwlog  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Fwlog";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_STARTDATE = "访问时间";
	public static final String ALIAS_STRDATE = "访问时间(毫秒)";
	public static final String ALIAS_TOMCAT = "Tomcat";
	public static final String ALIAS_USERCODE = "登陆账号";
	public static final String ALIAS_USERNAME = "姓名";
	public static final String ALIAS_SESSIONID = "sessionId";
	public static final String ALIAS_IP = "IP";
	public static final String ALIAS_FWURL = "访问菜单";
	public static final String ALIAS_MENUNAME = "菜单名称";
	public static final String ALIAS_ISQX = "是否有权限访问";
    */
	//date formats
	//public static final String FORMAT_STARTDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 访问时间
	 */
	private java.util.Date startDate;
	/**
	 * 访问时间(毫秒)
	 */
	private java.lang.String strDate;
	/**
	 * Tomcat
	 */
	private java.lang.String tomcat;
	/**
	 * 登陆账号
	 */
	private java.lang.String userCode;
	/**
	 * 姓名
	 */
	private java.lang.String userName;
	/**
	 * sessionId
	 */
	private java.lang.String sessionId;
	/**
	 * IP
	 */
	private java.lang.String ip;
	/**
	 * 访问菜单
	 */
	private java.lang.String fwUrl;
	/**
	 * 菜单名称
	 */
	private java.lang.String menuName;
	/**
	 * 是否有权限访问
	 */
	private java.lang.String isqx;
	//columns END 数据库字段结束
	
	private String ext;
	
	//concstructor

	public Fwlog(){
	}

	public Fwlog(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Fwlog_id")
	public java.lang.String getId() {
		return this.id;
	}
		/*
	public String getstartDateString() {
		return DateUtils.convertDate2String(FORMAT_STARTDATE, getstartDate());
	}
	public void setstartDateString(String value) throws ParseException{
		setstartDate(DateUtils.convertString2Date(FORMAT_STARTDATE,value));
	}*/
	
	public void setStartDate(java.util.Date value) {
		this.startDate = value;
	}
	
     @WhereSQL(sql="startDate=:Fwlog_startDate")
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	public void setStrDate(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.strDate = value;
	}
	
     @WhereSQL(sql="strDate=:Fwlog_strDate")
	public java.lang.String getStrDate() {
		return this.strDate;
	}
	public void setTomcat(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.tomcat = value;
	}
	
     @WhereSQL(sql="tomcat=:Fwlog_tomcat")
	public java.lang.String getTomcat() {
		return this.tomcat;
	}
	public void setUserCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userCode = value;
	}
	
     @WhereSQL(sql="userCode=:Fwlog_userCode")
	public java.lang.String getUserCode() {
		return this.userCode;
	}
	public void setUserName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userName = value;
	}
	
     @WhereSQL(sql="userName=:Fwlog_userName")
	public java.lang.String getUserName() {
		return this.userName;
	}
	public void setSessionId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.sessionId = value;
	}
	
     @WhereSQL(sql="sessionId=:Fwlog_sessionId")
	public java.lang.String getSessionId() {
		return this.sessionId;
	}
	public void setIp(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ip = value;
	}
	
     @WhereSQL(sql="ip=:Fwlog_ip")
	public java.lang.String getIp() {
		return this.ip;
	}
	public void setFwUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.fwUrl = value;
	}
	
     @WhereSQL(sql="fwUrl=:Fwlog_fwUrl")
	public java.lang.String getFwUrl() {
		return this.fwUrl;
	}
	public void setMenuName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.menuName = value;
	}
	
     @WhereSQL(sql="menuName=:Fwlog_menuName")
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	public void setIsqx(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.isqx = value;
	}
	
     @WhereSQL(sql="isqx=:Fwlog_isqx")
	public java.lang.String getIsqx() {
		return this.isqx;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("ID[").append(getId()).append("],")
			.append("访问时间[").append(getStartDate()).append("],")
			.append("访问时间(毫秒)[").append(getStrDate()).append("],")
			.append("Tomcat[").append(getTomcat()).append("],")
			.append("登陆账号[").append(getUserCode()).append("],")
			.append("姓名[").append(getUserName()).append("],")
			.append("sessionId[").append(getSessionId()).append("],")
			.append("IP[").append(getIp()).append("],")
			.append("访问菜单[").append(getFwUrl()).append("],")
			.append("菜单名称[").append(getMenuName()).append("],")
			.append("是否有权限访问[").append(getIsqx()).append("],")
			.toString();
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof Fwlog == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		Fwlog other = (Fwlog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Transient
	public String getExt() {
		if(StringUtils.isBlank(ext)){
			int year= Calendar.getInstance().get(Calendar.YEAR);
			this.ext= GlobalStatic.tableSuffix + year;
		}
			return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
}

	
