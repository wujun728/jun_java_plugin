package cn.org.rapid_framework.generator.provider.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.org.rapid_framework.generator.provider.db.DataSourceProvider;
import cn.org.rapid_framework.generator.provider.db.sql.model.Sql;
import cn.org.rapid_framework.generator.provider.db.sql.model.SqlParameter;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory.DatabaseMetaDataUtils;
import cn.org.rapid_framework.generator.provider.db.table.TableFactory.NotFoundTableException;
import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import cn.org.rapid_framework.generator.util.BeanHelper;
import cn.org.rapid_framework.generator.util.GLogger;
import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.sqlparse.BasicSqlFormatter;
import cn.org.rapid_framework.generator.util.sqlparse.NamedParameterUtils;
import cn.org.rapid_framework.generator.util.sqlparse.ParsedSql;
import cn.org.rapid_framework.generator.util.sqlparse.ResultSetMetaDataHolder;
import cn.org.rapid_framework.generator.util.sqlparse.SqlParseHelper;
import cn.org.rapid_framework.generator.util.sqlparse.SqlParseHelper.NameWithAlias;
import cn.org.rapid_framework.generator.util.typemapping.JdbcType;
/**
 * 
 * 根据SQL语句生成Sql对象,用于代码生成器的生成<br />
 * 
 * 示例使用：
 * <pre>
 * Sql sql = new SqlFactory().parseSql("select * from user_info where username=#username# and password=#password#");
 * </pre>
 * 
 * @author badqiu
 *
 */
public class SqlFactory {
    
    private List<SqlParameter> customParameters = new ArrayList<SqlParameter>();
    
    public SqlFactory() {
    }
    
	public SqlFactory(List<SqlParameter> customParameters) {
        this.customParameters = customParameters;
    }

    public Sql parseSql(String sourceSql) {
    	if(StringHelper.isBlank(sourceSql)) throw new IllegalArgumentException("sourceSql must be not empty");
    	String beforeProcessedSql = beforeParseSql(sourceSql);
    	
//    	String unscapedSourceSql = StringHelper.unescapeXml(beforeProcessedSql);
    	String namedSql = SqlParseHelper.convert2NamedParametersSql(beforeProcessedSql,":","");
        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(namedSql);
        String executeSql = new BasicSqlFormatter().format(NamedParameterUtils.substituteNamedParameters(parsedSql));
        
        Sql sql = new Sql();
        sql.setSourceSql(sourceSql);
        sql.setExecuteSql(executeSql);
        GLogger.debug("\n*******************************");
        GLogger.debug("sourceSql  :"+sql.getSourceSql());
        GLogger.debug("namedSql  :"+namedSql);
        GLogger.debug("executeSql :"+sql.getExecuteSql());
        GLogger.debug("*********************************");
        
        Connection conn = DataSourceProvider.getConnection();
        try {
        	if(!DatabaseMetaDataUtils.isHsqlDataBase(conn.getMetaData())){
        		conn.setReadOnly(true);
        	}
        	conn.setAutoCommit(false);
	        PreparedStatement ps = conn.prepareStatement(SqlParseHelper.removeOrders(executeSql));
	        ResultSetMetaData resultSetMetaData = executeForResultSetMetaData(executeSql,ps);
            sql.setColumns(new SelectColumnsParser().convert2Columns(sql,resultSetMetaData));
	        sql.setParams(new SqlParametersParser().parseForSqlParameters(parsedSql,sql));
	        
	        return afterProcessedSql(sql);
        }catch(SQLException e) {
        	throw new RuntimeException("execute sql occer error,\nexecutedSql:"+SqlParseHelper.removeOrders(executeSql),e);
        }catch(Exception e) {
        	throw new RuntimeException("sql parse error,\nexecutedSql:"+SqlParseHelper.removeOrders(executeSql),e);
        }finally {
        	try {
	        	conn.rollback();
	        	conn.close();
        	}catch(Exception e) {
        		throw new RuntimeException(e);
        	}
        }
    }

    protected Sql afterProcessedSql(Sql sql) {
		return sql;
	}

	protected String beforeParseSql(String sourceSql) {
		return sourceSql;
	}

    private ResultSetMetaData executeForResultSetMetaData(String executeSql,PreparedStatement ps)throws SQLException {
		SqlParseHelper.setRandomParamsValueForPreparedStatement(SqlParseHelper.removeOrders(executeSql), ps);
		ps.setMaxRows(3);
        ps.setFetchSize(3);
        ps.setQueryTimeout(20);
        try {
			ResultSet rs = ps.executeQuery();
			return rs.getMetaData(); 
		} catch (SQLException e) {
			try {
				return ps.getMetaData();
			}catch(SQLException ee) {
				throw e;
			}
		}
	}
	
    public class SelectColumnsParser {
    
		private LinkedHashSet<Column> convert2Columns(Sql sql,ResultSetMetaData metadata) throws SQLException, Exception {
			if(metadata == null) return new LinkedHashSet();
			LinkedHashSet<Column> columns = new LinkedHashSet();
	        for(int i = 1; i <= metadata.getColumnCount(); i++) {
	        	Column c = convert2Column(sql,metadata, i);
	        	if(c == null) throw new IllegalStateException("column must be not null");
				columns.add(c);
	        }
			return columns;
		}
	
        private Column convert2Column(Sql sql,ResultSetMetaData metadata, int i) throws SQLException, Exception {
			ResultSetMetaDataHolder m = new ResultSetMetaDataHolder(metadata, i);
			if(StringHelper.isNotBlank(m.getTableName())) {
				//FIXME 如果表有别名,将会找不到表,如 inner join user_info t1, tableName将为t1,应该转换为user_info
				Table table = foundTableByTableNameOrTableAlias(sql, m.getTableName());
				if(table == null) {
					return newColumn(null,m);
				}
			    Column column = table.getColumnBySqlName(m.getColumnNameOrLabel());
			    if(column == null || column.getSqlType() != m.getColumnType()) {
			        //可以再尝试解析sql得到 column以解决 password as pwd找不到column问题
			        column = newColumn(table,m);
			        GLogger.trace("not found column:"+m.getColumnNameOrLabel()+" on table:"+table.getSqlName()+" "+BeanHelper.describe(column));
			        //isInSameTable以此种判断为错误
			    }else {
			    	GLogger.trace("found column:"+m.getColumnNameOrLabel()+" on table:"+table.getSqlName()+" "+BeanHelper.describe(column));
			    }
			    return column;
			}else {
			    return newColumn(null,m);
			}
		}

		private Column newColumn(Table table,ResultSetMetaDataHolder m) {
			//Table table, int sqlType, String sqlTypeName,String sqlName, int size, int decimalDigits, boolean isPk,boolean isNullable, boolean isIndexed, boolean isUnique,String defaultValue,String remarks
			Column column = new Column(null,m.getColumnType(),m.getColumnTypeName(),m.getColumnNameOrLabel(),m.getColumnDisplaySize(),m.getScale(),false,false,false,false,null,null);
			GLogger.trace("not found on table by table emtpty:"+BeanHelper.describe(column));
			return column;
		}

		private Table foundTableByTableNameOrTableAlias(Sql sql,String tableNameId) throws Exception {
			try {
				return TableFactory.getInstance().getTable(tableNameId);
			}catch(NotFoundTableException e) {
				Set<NameWithAlias> tableNames = SqlParseHelper.getTableNamesByQuery(sql.getExecuteSql());
				for(NameWithAlias tableName : tableNames) {
					if(tableName.getAlias().equalsIgnoreCase(tableNameId)) {
						return TableFactory.getInstance().getTable(tableName.getName());
					}
				}
			}
			return null;
		}
    }

	public class SqlParametersParser {
		Map<String,Column> specialParametersMapping = new HashMap<String,Column>();
		{
			specialParametersMapping.put("offset", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","offset",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("limit", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","limit",0,0,false,false,false,false,null,null));
            specialParametersMapping.put("pageSize", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","pageSize",0,0,false,false,false,false,null,null));
            specialParametersMapping.put("pageNo", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","pageNo",0,0,false,false,false,false,null,null));
            specialParametersMapping.put("pageNumber", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","pageNumber",0,0,false,false,false,false,null,null));
            specialParametersMapping.put("page", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","page",0,0,false,false,false,false,null,null));
			
			specialParametersMapping.put("beginRow", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","beginRow",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("beginRows", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","beginRows",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("startRow", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","startRow",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("startRows", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","startRows",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("endRow", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","endRow",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("endRows", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","endRows",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("lastRow", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","lastRow",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("lastRows", new Column(null,JdbcType.INTEGER.TYPE_CODE,"INTEGER","lastRows",0,0,false,false,false,false,null,null));
			
			specialParametersMapping.put("orderBy", new Column(null,JdbcType.VARCHAR.TYPE_CODE,"VARCHAR","orderBy",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("orderby", new Column(null,JdbcType.VARCHAR.TYPE_CODE,"VARCHAR","orderby",0,0,false,false,false,false,null,null));
			specialParametersMapping.put("sortColumns", new Column(null,JdbcType.VARCHAR.TYPE_CODE,"VARCHAR","sortColumns",0,0,false,false,false,false,null,null));
		}
		private LinkedHashSet<SqlParameter> parseForSqlParameters(ParsedSql parsedSql,Sql sql) throws Exception {
			LinkedHashSet<SqlParameter> result = new LinkedHashSet<SqlParameter>();
			long start = System.currentTimeMillis();
			for(int i = 0; i < parsedSql.getParameterNames().size(); i++) {
				String paramName = parsedSql.getParameterNames().get(i);
				Column column = findColumnByParamName(parsedSql, sql, paramName);
				if(column == null) {
					column = specialParametersMapping.get(paramName);
					if(column == null) {
						//FIXME 不能猜测的column类型
						column = new Column(null,JdbcType.UNDEFINED.TYPE_CODE,"UNDEFINED",paramName,0,0,false,false,false,false,null,null);
					}
				}
				SqlParameter param = new SqlParameter(column);
				
				param.setParamName(paramName);
				if(isMatchListParam(sql.getSourceSql(), paramName)) { //FIXME 只考虑(:username)未考虑(#inUsernames#) and (#{inPassword}),并且可以使用 #inUsername[]#
					param.setListParam(true);
				}
				result.add(param);			
			}
			GLogger.perf("parseForSqlParameters() cost:"+(System.currentTimeMillis()- start));
			return result;
	    
		}

		public boolean isMatchListParam(String sql, String paramName) {
			return 
			    sql.matches("(?s).*\\sin\\s*\\([:#\\$&]\\{?"+paramName+"\\}?[$#}]?\\).*") // match in (:username) ,not in (#username#)
			    || sql.matches("(?s).*[#$]"+paramName+"\\[]\\.?\\w*[#$].*") //match #user[]# $user[]$ #user[].age# for ibatis
			   || sql.matches("(?s).*[#$]\\{"+paramName+"\\[[$\\{\\}\\w]+]\\}*.*"); //match #{user[index]}# ${user[${index}]}  for mybatis
		}
	
		private Column findColumnByParamName(ParsedSql parsedSql,Sql sql, String paramName) throws Exception {
		    for(SqlParameter customParam : customParameters) {
		        if(customParam.getParamName().equals(paramName)) {
		            return customParam;
		        }
		    }
			Column column = sql.getColumnByName(paramName);
			if(column == null) {
				//FIXME 还未处理 t.username = :username的t前缀问题
				column = findColumnByParseSql(parsedSql, SqlParseHelper.getColumnNameByRightCondition(parsedSql.toString(), paramName) );
			}
			if(column == null) {
				column = findColumnByParseSql(parsedSql, paramName);
			}
			return column;
		}
	
		private Column findColumnByParseSql(ParsedSql sql, String paramName) throws Exception {
			Collection<NameWithAlias> tableNames = SqlParseHelper.getTableNamesByQuery(sql.toString());
			for(NameWithAlias tableName : tableNames) {
				Table t = TableFactory.getInstance().getTable(tableName.getName());
				if(t != null) {
					Column column = t.getColumnByName(paramName);
					if(column != null) {
						return column;
					}
				}
			}
			return null;
		}
	}
    
    public static void main(String[] args) throws Exception {
    	// ? parameters
//    	SelectSqlMetaData t1 = new SqlQueryFactory().getByQuery("select * from user_info");
//    	SelectSqlMetaData t2 = new SqlQueryFactory().getByQuery("select user_info.username,password pwd from user_info where username=? and password =?");
//    	SelectSqlMetaData t3 = new SqlQueryFactory().getByQuery("select username,password,role.role_name,role_desc from user_info,role where user_info.user_id = role.user_id and username=? and password =?");
//    	SelectSqlMetaData t4 = new SqlQueryFactory().getByQuery("select count(*) cnt from user_info,role where user_info.user_id = role.user_id and username=? and password =?");
//    	SelectSqlMetaData t5 = new SqlQueryFactory().getByQuery("select sum(age) from user_info,role where user_info.user_id = role.user_id and username=? and password =?");
//    	SelectSqlMetaData t6 = new SqlQueryFactory().getByQuery("select username,password,role_desc from user_info,role where user_info.user_id = role.user_id and username=? and password =? limit ?,?");
//    	SelectSqlMetaData t7 = new SqlQueryFactory().getByQuery("select username,password,count(role_desc) role_desc_cnt from user_info,role where user_info.user_id = role.user_id group by username");
//    
    	Sql n2 = new SqlFactory().parseSql("select user_info.username,password pwd from user_info where username=:username and password =:password");
    	Sql n3 = new SqlFactory().parseSql("select username,password,role.role_name,role_desc from user_info,role where user_info.user_id = role.user_id and username=:username and password =:password");
    	Sql n4 = new SqlFactory().parseSql("select count(*) cnt from user_info,role where user_info.user_id = role.user_id and username=:username and password =:password");
    	Sql n5 = new SqlFactory().parseSql("select sum(age) from user_info,role where user_info.user_id = role.user_id and username=:username and password =:password");
    	Sql n7 = new SqlFactory().parseSql("select username,password,count(role_desc) role_desc_cnt from user_info,role where user_info.user_id = role.user_id group by username");
    	Sql n8 = new SqlFactory().parseSql("select username,password,count(role_desc) role_desc_cnt from user_info,role where user_info.user_id = :userId group by username");
    	new SqlFactory().parseSql("select username,password,role_desc from user_info,role where user_info.user_id = role.user_id and username=:username and password =:password and birth_date between :birthDateBegin and :birthDateEnd");
    	new SqlFactory().parseSql("select username,password,role_desc from user_info,role where user_info.user_id = role.user_id and username=:username and password =:password and birth_date between :birthDateBegin and :birthDateEnd limit :offset,:limit");
    }
    
}
