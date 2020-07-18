package cn.org.rapid_framework.generator.util.typemapping;

import java.sql.Types;
/**
 * 用于ibatis的JdbcType
 * @author badqiu
 */
public enum JdbcType {

  BIT(Types.BIT),
  TINYINT(Types.TINYINT),
  SMALLINT(Types.SMALLINT),
  INTEGER(Types.INTEGER),
  BIGINT(Types.BIGINT),
  FLOAT(Types.FLOAT),
  REAL(Types.REAL),
  DOUBLE(Types.DOUBLE),
  NUMERIC(Types.NUMERIC),
  DECIMAL(Types.DECIMAL),
  CHAR(Types.CHAR),
  VARCHAR(Types.VARCHAR),
  LONGVARCHAR(Types.LONGVARCHAR),
  DATE(Types.DATE),
  TIME(Types.TIME),
  TIMESTAMP(Types.TIMESTAMP),
  BINARY(Types.BINARY),
  VARBINARY(Types.VARBINARY),
  LONGVARBINARY(Types.LONGVARBINARY),
  NULL(Types.NULL),
  OTHER(Types.OTHER),
  BLOB(Types.BLOB),
  CLOB(Types.CLOB),
  BOOLEAN(Types.BOOLEAN),
  CURSOR(-10), // Oracle
  UNDEFINED(Integer.MIN_VALUE + 1000),
  NVARCHAR(-9),
  NCHAR(-15),
  NCLOB(2011);

  public final int TYPE_CODE;

  JdbcType(int code) {
    this.TYPE_CODE = code;
  }
  
  public static String getJdbcSqlTypeName(int code) {
	  for(JdbcType type : values()) {
		  if(type.TYPE_CODE == code) {
			  return type.name();
		  }
	  }
	  return null;
  }

}