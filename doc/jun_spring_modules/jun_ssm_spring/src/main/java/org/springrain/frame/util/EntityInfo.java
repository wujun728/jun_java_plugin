package org.springrain.frame.util;

import java.util.List;

/**
* 记录缓存数据库一个表对应的Entity的信息
*
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-03-19 11:08:15
 * @see org.springrain.frame.util.EntityInfo
*/
public class EntityInfo {
	
	private String tableName=null;
	private String className=null;
	//实体类表后缀的名称
	private String tableSuffix="";
	//注解标识的后缀字段
	private String tableSuffixFieldName=null;
	//是否分表
    private Boolean tableSuffixAnnotation=false;
	
    
	private Class<?> pkReturnType;
	
	
	private String pkName=null;

	
	
	//主键序列
	private String pksequence=null;
	// 是否不记录日志,默认false 为记录
	private Boolean notLog=false;
	
	//是否包含LuceneSearch注解
	private Boolean luceneSearchAnnotation=false;
	//是否包含Table注解
	private Boolean tableAnnotation=false;
	
	//包含的字段信息
	private List<FieldInfo> fields;
	
	
	/**
	 * 数据库的表名
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 数据库表映射的实体类名
	 * @return
	 */
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	/**
	 * 数据库分表的后缀名 例如 _history_2012
	 * @return
	 */
	public String getTableSuffix() {
		return tableSuffix;
	}
	public void setTableSuffix(String tableSuffix) {
		this.tableSuffix = tableSuffix;
	}
	/**
	 * 获取table主键对应Enitty属性名称
	 * @return String
	 */
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public Boolean getTableSuffixAnnotation() {
		return tableSuffixAnnotation;
	}
	public void setTableSuffixAnnotation(Boolean tableSuffixAnnotation) {
		this.tableSuffixAnnotation = tableSuffixAnnotation;
	}
	public String getPksequence() {
		return pksequence;
	}
	public void setPksequence(String pksequence) {
		this.pksequence = pksequence;
	}
	public Boolean isNotLog() {
		return notLog;
	}
	public void setNotLog(Boolean notLog) {
		this.notLog = notLog;
	}
	@SuppressWarnings("rawtypes")
	public Class getPkReturnType() {
		return pkReturnType;
	}
	@SuppressWarnings("rawtypes")
	public void setPkReturnType(Class pkReturnType) {
		this.pkReturnType = pkReturnType;
	}
    public Boolean getLuceneSearchAnnotation() {
        return luceneSearchAnnotation;
    }
    public void setLuceneSearchAnnotation(Boolean luceneSearchAnnotation) {
        this.luceneSearchAnnotation = luceneSearchAnnotation;
    }
    public Boolean getTableAnnotation() {
        return tableAnnotation;
    }
    public void setTableAnnotation(Boolean tableAnnotation) {
        this.tableAnnotation = tableAnnotation;
    }
    public List<FieldInfo> getFields() {
        return fields;
    }
    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }
    public String getTableSuffixFieldName() {
        return tableSuffixFieldName;
    }
    public void setTableSuffixField(String tableSuffixFieldName) {
        this.tableSuffixFieldName = tableSuffixFieldName;
    }


}
