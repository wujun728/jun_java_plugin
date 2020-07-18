package cn.org.rapid_framework.generator.provider.db.table.model;



import java.util.List;

import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.provider.db.table.model.ForeignKey.ReferenceKey;
import cn.org.rapid_framework.generator.provider.db.table.model.util.ColumnHelper;
import cn.org.rapid_framework.generator.util.GLogger;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.TestDataGenerator;
import cn.org.rapid_framework.generator.util.typemapping.ActionScriptDataTypesUtils;
import cn.org.rapid_framework.generator.util.typemapping.DatabaseDataTypesUtils;
import cn.org.rapid_framework.generator.util.typemapping.JavaPrimitiveTypeMapping;
import cn.org.rapid_framework.generator.util.typemapping.JdbcType;

/**
 * 用于生成代码的Columb对象.对应数据库表column
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class Column implements java.io.Serializable,Cloneable{
	/**
	 * Reference to the containing table
	 */
	private Table _table;

	/**
	 * The java.sql.Types type
	 */
	private int _sqlType;

	/**
	 * The sql typename. provided by JDBC driver
	 */
	private String _sqlTypeName;

	/**
	 * The name of the column
	 */
	private String _sqlName;

	/**
	 * True if the column is a primary key
	 */
	private boolean _isPk;

	/**
	 * True if the column is a foreign key
	 */
	private boolean _isFk;

	/**
	 * @todo-javadoc Describe the column
	 */
	private int _size;

	/**
	 * @todo-javadoc Describe the column
	 */
	private int _decimalDigits;

	/**
	 * True if the column is nullable
	 */
	private boolean _isNullable;

	/**
	 * True if the column is indexed
	 */
	private boolean _isIndexed;

	/**
	 * True if the column is unique
	 */
	private boolean _isUnique;

	/**
	 * Null if the DB reports no default value
	 */
	private String _defaultValue;
	
	/**
	 * The comments of column
	 */
	private String _remarks;
			
	/**
	 * @param table
	 * @param sqlType
	 * @param sqlTypeName
	 * @param sqlName
	 * @param size
	 * @param decimalDigits
	 * @param isPk
	 * @param isNullable
	 * @param isIndexed
	 * @param isUnique
	 * @param defaultValue
	 * @param remarks
	 */
	public Column(Table table, int sqlType, String sqlTypeName,
			String sqlName, int size, int decimalDigits, boolean isPk,
			boolean isNullable, boolean isIndexed, boolean isUnique,
			String defaultValue,String remarks) {
		if(sqlName == null) throw new NullPointerException();
		_table = table;
		_sqlType = sqlType;
		_sqlName = sqlName;
		_sqlTypeName = sqlTypeName;
		_size = size;
		_decimalDigits = decimalDigits;
		_isPk = isPk;
		_isNullable = isNullable;
		_isIndexed = isIndexed;
		_isUnique = isUnique;
		_defaultValue = defaultValue;
		_remarks = remarks;
		
		GLogger.trace(sqlName + " isPk -> " + _isPk);
		
		initOtherProperties();
	}

	public Column(Column c) {
        this(c.getTable(),
           c.getSqlType(),
           c.getSqlTypeName(),
           c.getSqlName(),
           c.getSize(),
           c.getDecimalDigits(),
           c.isPk(),
           c.isNullable(),
           c.isIndexed(),
           c.isUnique(),
           c.getDefaultValue(),
           c.getRemarks());
	}
	
	public Column() {
	}
	
	/**
	 * Gets the SqlType attribute of the Column object
	 * 
	 * @return The SqlType value
	 */
	public int getSqlType() {
		return _sqlType;
	}

	/**
	 * Gets the Table attribute of the DbColumn object
	 * 
	 * @return The Table value
	 */
	public Table getTable() {
		return _table;
	}

	/**
	 * Gets the Size attribute of the DbColumn object
	 * 
	 * @return The Size value
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * Gets the DecimalDigits attribute of the DbColumn object
	 * 
	 * @return The DecimalDigits value
	 */
	public int getDecimalDigits() {
		return _decimalDigits;
	}

	/**
	 * Gets the SqlTypeName attribute of the Column object
	 * 
	 * @return The SqlTypeName value
	 */
	public String getSqlTypeName() {
		return _sqlTypeName;
	}

	/**
	 * Gets the SqlName attribute of the Column object
	 * 
	 * @return The SqlName value
	 */
	public String getSqlName() {
		if(_sqlName == null) throw new NullPointerException();
		return _sqlName;
	}

	
	/**
	 * Gets the Pk attribute of the Column object
	 * 
	 * @return The Pk value
	 */
	public boolean isPk() {
		return _isPk;
	}

	/**
	 * Gets the Fk attribute of the Column object
	 * 
	 * @return The Fk value
	 */
	public boolean isFk() {
		return _isFk;
	}

	/**
	 * Gets the Nullable attribute of the Column object
	 * 
	 * @return The Nullable value
	 */
	public  boolean isNullable() {
		return _isNullable;
	}

	/**
	 * Gets the Indexed attribute of the DbColumn object
	 * 
	 * @return The Indexed value
	 */
	public  boolean isIndexed() {
		return _isIndexed;
	}

	/**
	 * Gets the Unique attribute of the DbColumn object
	 * 
	 * @return The Unique value
	 */
	public boolean isUnique() {
		return _isUnique;
	}

	/**
	 * Gets the DefaultValue attribute of the DbColumn object
	 * 
	 * @return The DefaultValue value
	 */
	public  String getDefaultValue() {
		return _defaultValue;
	}

    /**
     * 列的数据库备注
     * @return
     */
	public  String getRemarks() {
		return _remarks;
	}

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }
    
    public void setNullable(boolean v) {
        this._isNullable = v;
    }
    
    public void setUnique(boolean unique) {
        _isUnique = unique;
    }

    public void setPk(boolean v) {
        this._isPk = v;
    }	
	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public int hashCode() {
		if(getTable() != null) {
			return (getTable().getSqlName() + "#" + getSqlName()).hashCode();
		}else {
			return (getSqlName()).hashCode();
		}
	}

	/**
	 * Describe what the method does
	 * 
	 * @param o
	 *            Describe what the parameter does
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for return value
	 */
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Column) {
			Column other = (Column)o;
			if(getSqlName().equals(other.getSqlName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public String toString() {
		return getSqlName();
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			//ignore
			return null;
		}
	}
	
	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	protected  String prefsPrefix() {
		return "tables/" + getTable().getSqlName() + "/columns/" + getSqlName();
	}

	/**
	 * Sets the Pk attribute of the DbColumn object
	 * 
	 * @param flag
	 *            The new Pk value
	 */
	void setFk(boolean flag) {
		_isFk = flag;
	}
	
	public String getUnderscoreName() {
		return getSqlName().toLowerCase();
	}

    /** 
     * 根据列名，根据sqlName计算得出，示例值： BirthDate
     **/
	public String getColumnName() {
		return columnName;
	}

    /** 
     * 第一个字母小写的columName,等价于: StringHelper.uncapitalize(getColumnName()),示例值: birthDate
     **/
	public String getColumnNameFirstLower() {
		return StringHelper.uncapitalize(getColumnName());
	}

    /** 
     * 全部小写的columName,等价于: getColumnName().toLowerCase(),示例值: birthdate
     **/
	public String getColumnNameLowerCase() {
		return getColumnName().toLowerCase();
	}

    /**
     * 使用 getColumnNameFirstLower()替换
     * @deprecated use getColumnNameFirstLower() instead
     */
	public String getColumnNameLower() {
		return getColumnNameFirstLower();
	}

    /**
     * 得到 jdbcSqlType类型名称，示例值:VARCHAR,DECIMAL, 现Ibatis3使用该属性
     */
	public String getJdbcSqlTypeName() {
		return getJdbcType();
	}
	
	/**
     * 得到 jdbcSqlType类型名称，示例值:VARCHAR,DECIMAL, 现Ibatis3使用该属性
     */
    public String getJdbcType() {
        String result = JdbcType.getJdbcSqlTypeName(getSqlType());
        return result;
    }
    /**
     * 列的别名，等价于：getRemarks().isEmpty() ? getColumnNameFirstLower() : getRemarks()
     * 
     * <br />
     * 示例值: birthDate
     */
	public String getColumnAlias() {
		return columnAlias;
	}

    /**
     * 列的常量名称
     * 
     * <br />
     * 示例值: BIRTH_DATE
     */
	public String getConstantName() {
		return StringHelper.toUnderscoreName(getColumnName()).toUpperCase();
	}
	
	/**
	 * 
	 * @deprecated
	 */
	public boolean getIsNotIdOrVersionField() {
		return !isPk();
	}
	
	/**得到 rapid-validation的验证表达式: required min-value-800  */
	public String getValidateString() {
		return isNullable() ? getNoRequiredValidateString() :  "required " + getNoRequiredValidateString();
	}
	
	/**得到 rapid-validation的验证表达式: min-value-800  */
	public String getNoRequiredValidateString() {
		return ColumnHelper.getRapidValidation(this);
	}

	/** 得到JSR303 bean validation(Hibernate Validator)的验证表达式: @NotNull @Min(100) @Max(800) */
	public String[] getHibernateValidatorConstraintNames() {
		return ColumnHelper.removeHibernateValidatorSpecialTags(getHibernateValidatorExprssion());
	}
	
	/** 得到JSR303 bean validation(Hibernate Validator)的验证表达式: @NotNull @Min(100) @Max(800) */
	public String getHibernateValidatorExprssion() {
		return hibernateValidatorExprssion;
	}

	public void setHibernateValidatorExprssion(String v) {
		hibernateValidatorExprssion = v;
	}
	
	/** 列是否是String类型 */
	public boolean getIsStringColumn() {
		return DatabaseDataTypesUtils.isString(getJavaType());
	}
	
	/** 列是否是日期类型 */
	public boolean getIsDateTimeColumn() {
		return DatabaseDataTypesUtils.isDate(getJavaType());
	}
	
	/** 列是否是Number类型 */
	public boolean getIsNumberColumn() {
		return DatabaseDataTypesUtils.isFloatNumber(getJavaType()) 
			|| DatabaseDataTypesUtils.isIntegerNumber(getJavaType());
	}
	
	/** 检查是否包含某些关键字,关键字以逗号分隔 */
	public boolean contains(String keywords) {
		if(keywords == null) throw new IllegalArgumentException("'keywords' must be not null");
		return StringHelper.contains(getSqlName(), keywords.split(","));
	}
	
	public boolean isHtmlHidden() {
		return isPk() && _table.isSingleId();
	}

    /**
     * 得到对应的javaType,如java.lang.String,
     * @return
     */
	public String getJavaType() {
		return javaType;
	}

    /**
     * 得到简短的javaType的名称，如com.company.model.UserInfo,将返回 UserInfo
     * @return
     */
	public String getSimpleJavaType() {
		return StringHelper.getJavaClassSimpleName(getJavaType());
	}
	/**
     * 得到尽可能简短的javaType的名称，如果是java.lang.String,将返回String, 如com.company.model.UserInfo,将返回 com.company.model.UserInfo
     * @return
     */
	public String getPossibleShortJavaType() {
	    if(getJavaType().startsWith("java.lang.")) {
	        return getSimpleJavaType();
	    }else {
	        return getJavaType();
	    }
    }

	public boolean isPrimitive() {
	    return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType()) != null;
	}
    /**
     * 得到原生类型的javaType,如java.lang.Integer将返回int,而非原生类型,将直接返回getSimpleJavaType()
     * @return
     */	
	public String getPrimitiveJavaType() {
		return JavaPrimitiveTypeMapping.getPrimitiveType(getSimpleJavaType());
	}
	
	/** 得到ActionScript的映射类型,用于Flex代码的生成  */
	public String getAsType() {
		return asType;
	}
	
	/** 得到列的测试数据  */
	public String getTestData() {
		return new TestDataGenerator().getDBUnitTestData(getColumnName(),getJavaType(),getSize());
	}
	
	/** 列是否可以更新  */
	public boolean isUpdatable() {
		return updatable;
	}

	/** 列是否可以插入  */
	public boolean isInsertable() {
		return insertable;
	}
	
	/** 得到枚举(enum)的类名称，示例值：SexEnum  */
	public String getEnumClassName() {
		return enumClassName;
	}
	
	/** 枚举值,以分号分隔,示例值:M(1,男);F(0,女) 或者是:M(男);F(女)  */
	public void setEnumString(String str) {
		this.enumString = str;
	}
	/** 枚举值,以分号分隔,示例值:M(1,男);F(0,女) 或者是:M(男);F(女)  */
	public String getEnumString() {
		return enumString;
	}
	/** 解析getEnumString()字符串转换为List<EnumMetaDada>对象  */
	public List<EnumMetaDada> getEnumList() {
		return StringHelper.string2EnumMetaData(getEnumString());
	}
	/** 是否是枚举列，等价于:return getEnumList() != null && !getEnumList().isEmpty()  */
	public boolean isEnumColumn() {
		return getEnumList() != null && !getEnumList().isEmpty();
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setAsType(String asType) {
		this.asType = asType;
	}

	public void setEnumClassName(String enumClassName) {
		this.enumClassName = enumClassName;
	}
	
	

//	public void setBelongsTo(String foreignKey) {
//		ReferenceKey ref = ReferenceKey.fromString(foreignKey);
//		if(ref != null && _table != null) {
//			_table.getImportedKeys().addForeignKey(ref.tableName, ref.columnSqlName, getSqlName(), ref.columnSqlName.hashCode());
//		}
//	}
//	
//	public void setHasAndBelongsToMany(String foreignKey) {
//	}

	private ReferenceKey hasOne;
	public String getHasOne() {
		return ReferenceKey.toString(hasOne);
	}
	
	/** nullValue for ibatis sqlmap: <result property="age" column="age" nullValue="0"  /> */
	public String getNullValue() {
		return JavaPrimitiveTypeMapping.getDefaultValue(getJavaType());
	}

	public boolean isHasNullValue() {
		return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType()) != null;
	}
	
    /**
     * 设置many-to-one,foreignKey格式: fk_table_name(fk_column) 或者 schema_name.fk_table_name(fk_column)
     * @param foreignKey
     * @return
     */
	public void setHasOne(String foreignKey) {
		hasOne = ReferenceKey.fromString(foreignKey);
		if(hasOne != null && _table != null) {
//			Table refTable = TableFactory.getInstance().getTable(hasOne.tableName);
//			_table.getImportedKeys().addForeignKey(refTable.getSqlName(), hasOne.columnSqlName, getSqlName(), hasOne.columnSqlName.toLowerCase().hashCode());
			_table.getImportedKeys().addForeignKey(hasOne.tableName, hasOne.columnSqlName, getSqlName(), hasOne.columnSqlName.toLowerCase().hashCode());
		}
	}
	
	private ReferenceKey hasMany = null;
	public String getHasMany() {
		return ReferenceKey.toString(hasMany);
	}

    /**
     * 设置one-to-many,foreignKey格式: fk_table_name(fk_column) 或者 schema_name.fk_table_name(fk_column)
     * @param foreignKey
     * @return
     */
	public void setHasMany(String foreignKey) {
		hasMany = ReferenceKey.fromString(foreignKey);
		if(hasMany != null && _table != null) {
//			Table refTable = TableFactory.getInstance().getTable(hasMany.tableName);
//			_table.getExportedKeys().addForeignKey(refTable.getSqlName(), hasMany.columnSqlName, getSqlName(), hasMany.columnSqlName.toLowerCase().hashCode());
			_table.getExportedKeys().addForeignKey(hasMany.tableName, hasMany.columnSqlName, getSqlName(), hasMany.columnSqlName.toLowerCase().hashCode());
		}
	}

	private void initOtherProperties() {
		String normalJdbcJavaType = DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
		javaType = GeneratorProperties.getProperty("java_typemapping."+normalJdbcJavaType,normalJdbcJavaType).trim();
		columnName = StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(getSqlName()));
		enumClassName = getColumnName()+"Enum";		
		asType = ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());	
		columnAlias = StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getColumnNameFirstLower()));
		setHibernateValidatorExprssion(ColumnHelper.getHibernateValidatorExpression(this));
	}
	
	/** 删除聚集函数的相关char,示例转换 count(*) => count, max(age) => max_age, sum(income) => sum_income */
    public static String removeAggregationColumnChars(String columSqlName) {
        return columSqlName.replace('(', '_').replace(")", "").replace("*", "");
    }
	
	private String enumString = "";
	private String javaType;
	private String columnAlias;
	private String columnName;
	private String asType;	
	private String enumClassName;
	private boolean updatable = true;	
	private boolean insertable = true;
	private String hibernateValidatorExprssion;
//	private String rapidValidation;
	/**
	 * public enum ${enumClassName} {
	 * 		${enumAlias}(${enumKey},${enumDesc});
	 * 		private String key;
	 * 		private String value;
	 * }
	 * @author badqiu
	 */
	public static class EnumMetaDada {
		private String enumAlias;
		private String enumKey;
		private String enumDesc;
		public EnumMetaDada(String enumAlias, String enumKey, String enumDesc) {
			super();
			this.enumAlias = enumAlias;
			this.enumKey = enumKey;
			this.enumDesc = enumDesc;
		}
		
		public String getEnumAlias() {
			return enumAlias;
		}
		public String getEnumKey() {
			return enumKey;
		}
		public String getEnumDesc() {
			return enumDesc;
		}
	}
}
