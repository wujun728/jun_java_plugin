package org.springrain.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 14:16:38
 * @see org.springrain.demo.entity.Tableindex
 */
@Table(name="t_tableindex")
public class Tableindex  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Tableindex";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_TABLENAME = "表名";
	public static final String ALIAS_MAXINDEX = "表记录最大的行,一直累加";
	public static final String ALIAS_PREFIX = "前缀 单个字母加 _";
    */
	//date formats
	
	//columns START
	/**
	 * 表名
	 */
	private java.lang.String id;
	
	/**
	 * 表记录最大的行,一直累加
	 */
	private java.lang.Integer maxIndex;
	/**
	 * 前缀 单个字母加 _
	 */
	private java.lang.String prefix;
	//columns END 数据库字段结束
	
	//concstructor

	public Tableindex(){
	}

	public Tableindex(
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
     @WhereSQL(sql="id=:Tableindex_id")
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setMaxIndex(java.lang.Integer value) {
		this.maxIndex = value;
	}
	
     @WhereSQL(sql="maxIndex=:Tableindex_maxIndex")
	public java.lang.Integer getMaxIndex() {
		return this.maxIndex;
	}
	public void setPrefix(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.prefix = value;
	}
	
     @WhereSQL(sql="prefix=:Tableindex_prefix")
	public java.lang.String getPrefix() {
		return this.prefix;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("表名[").append(getId()).append("],")
			.append("表记录最大的行,一直累加[").append(getMaxIndex()).append("],")
			.append("前缀 单个字母加 _[").append(getPrefix()).append("],")
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
		if(obj instanceof Tableindex == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		Tableindex other = (Tableindex)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
