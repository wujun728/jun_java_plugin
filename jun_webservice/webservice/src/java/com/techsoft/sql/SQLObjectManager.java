package com.techsoft.sql;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import oracle.jdbc.OracleTypes;

import com.techsoft.Cache;
import com.techsoft.ConnectionPool;
import com.techsoft.DataExecutor;
import com.techsoft.MetaData;
import com.techsoft.SQLObject;
import com.techsoft.SQLParam;
import com.techsoft.TechException;
import com.techsoft.cache.CacheFactory;
import com.techsoft.container.DataServer;
import com.techsoft.executor.DataExecutorFactory;

public class SQLObjectManager {
	private static SQLObjectManager instance;
	private static String sqlCacheName = "1000";
	private Cache<String, SQLObject> orasqlcache;
	private ReadWriteLock lock = null;
	private Lock readLock = null;
	private Lock writeLock = null;
	private SQLObject oraclesql = null;

	private void buildOracleSQLObject() {
		oraclesql.setSelectSql(OracleSql.SELECT_ALL_SQL);
		oraclesql.setInsertSql(OracleSql.INSERT_SQL);
		oraclesql.setDeleteSql(OracleSql.DELETE_SQL);

		SQLParam SQLID = new SQLParam();
		SQLID.setDtype("12");
		SQLID.setDtypename("VARCHAR");
		SQLID.setIndex("1,2,37");
		SQLID.setIotype(SQLParam.ParamType.in.name());
		SQLID.setName("SQLID");
		// SQLID.buildPositions();
		oraclesql.getInsertParams().add(SQLID);

		SQLParam SQLNAME = new SQLParam();
		SQLNAME.setDtype("12");
		SQLNAME.setDtypename("VARCHAR");
		SQLNAME.setIndex("3,20");
		SQLNAME.setIotype(SQLParam.ParamType.in.name());
		SQLNAME.setName("SQLNAME");
		// SQLNAME.buildPositions();
		oraclesql.getInsertParams().add(SQLNAME);

		SQLParam DEF_SELECT_SQL = new SQLParam();
		DEF_SELECT_SQL.setDtype("2005");
		DEF_SELECT_SQL.setDtypename("CLOB");
		DEF_SELECT_SQL.setIndex("4,21");
		DEF_SELECT_SQL.setIotype(SQLParam.ParamType.in.name());
		DEF_SELECT_SQL.setName("DEF_SELECT_SQL");
		// DEF_SELECT_SQL.buildPositions();
		oraclesql.getInsertParams().add(DEF_SELECT_SQL);

		SQLParam DEF_SELECT_PARAMS = new SQLParam();
		DEF_SELECT_PARAMS.setDtype("2005");
		DEF_SELECT_PARAMS.setDtypename("CLOB");
		DEF_SELECT_PARAMS.setIndex("5,22");
		DEF_SELECT_PARAMS.setIotype(SQLParam.ParamType.in.name());
		DEF_SELECT_PARAMS.setName("DEF_SELECT_PARAMS");
		// DEF_SELECT_PARAMS.buildPositions();
		oraclesql.getInsertParams().add(DEF_SELECT_PARAMS);

		SQLParam DEF_SELECT_METADATA = new SQLParam();
		DEF_SELECT_METADATA.setDtype("2005");
		DEF_SELECT_METADATA.setDtypename("CLOB");
		DEF_SELECT_METADATA.setIndex("6,23");
		DEF_SELECT_METADATA.setIotype(SQLParam.ParamType.in.name());
		DEF_SELECT_METADATA.setName("DEF_SELECT_METADATA");
		// DEF_SELECT_METADATA.buildPositions();
		oraclesql.getInsertParams().add(DEF_SELECT_METADATA);

		SQLParam DEF_INSERT_SQL = new SQLParam();
		DEF_INSERT_SQL.setDtype("2005");
		DEF_INSERT_SQL.setDtypename("CLOB");
		DEF_INSERT_SQL.setIndex("7,24");
		DEF_INSERT_SQL.setIotype(SQLParam.ParamType.in.name());
		DEF_INSERT_SQL.setName("DEF_INSERT_SQL");
		// DEF_INSERT_SQL.buildPositions();
		oraclesql.getInsertParams().add(DEF_INSERT_SQL);

		SQLParam DEF_INSERT_PARAMS = new SQLParam();
		DEF_INSERT_PARAMS.setDtype("2005");
		DEF_INSERT_PARAMS.setDtypename("CLOB");
		DEF_INSERT_PARAMS.setIndex("8,25");
		DEF_INSERT_PARAMS.setIotype(SQLParam.ParamType.in.name());
		DEF_INSERT_PARAMS.setName("DEF_INSERT_PARAMS");
		// DEF_INSERT_PARAMS.buildPositions();
		oraclesql.getInsertParams().add(DEF_INSERT_PARAMS);

		SQLParam DEF_UPDATE_SQL = new SQLParam();
		DEF_UPDATE_SQL.setDtype("2005");
		DEF_UPDATE_SQL.setDtypename("CLOB");
		DEF_UPDATE_SQL.setIndex("9,26");
		DEF_UPDATE_SQL.setIotype(SQLParam.ParamType.in.name());
		DEF_UPDATE_SQL.setName("DEF_UPDATE_SQL");
		// DEF_UPDATE_SQL.buildPositions();
		oraclesql.getInsertParams().add(DEF_UPDATE_SQL);

		SQLParam DEF_UPDATE_PARAMS = new SQLParam();
		DEF_UPDATE_PARAMS.setDtype("2005");
		DEF_UPDATE_PARAMS.setDtypename("CLOB");
		DEF_UPDATE_PARAMS.setIndex("10,27");
		DEF_UPDATE_PARAMS.setIotype(SQLParam.ParamType.in.name());
		DEF_UPDATE_PARAMS.setName("DEF_UPDATE_PARAMS");
		// DEF_UPDATE_PARAMS.buildPositions();
		oraclesql.getInsertParams().add(DEF_UPDATE_PARAMS);

		SQLParam DEF_DELETE_SQL = new SQLParam();
		DEF_DELETE_SQL.setDtype("2005");
		DEF_DELETE_SQL.setDtypename("CLOB");
		DEF_DELETE_SQL.setIndex("11,28");
		DEF_DELETE_SQL.setIotype(SQLParam.ParamType.in.name());
		DEF_DELETE_SQL.setName("DEF_DELETE_SQL");
		// DEF_DELETE_SQL.buildPositions();
		oraclesql.getInsertParams().add(DEF_DELETE_SQL);

		SQLParam DEF_DELETE_PARAMS = new SQLParam();
		DEF_DELETE_PARAMS.setDtype("2005");
		DEF_DELETE_PARAMS.setDtypename("CLOB");
		DEF_DELETE_PARAMS.setIndex("12,29");
		DEF_DELETE_PARAMS.setIotype(SQLParam.ParamType.in.name());
		DEF_DELETE_PARAMS.setName("DEF_DELETE_PARAMS");
		// DEF_DELETE_PARAMS.buildPositions();
		oraclesql.getInsertParams().add(DEF_DELETE_PARAMS);

		SQLParam SELECT_SQL = new SQLParam();
		SELECT_SQL.setDtype("2005");
		SELECT_SQL.setDtypename("CLOB");
		SELECT_SQL.setIndex("13,30");
		SELECT_SQL.setIotype(SQLParam.ParamType.in.name());
		SELECT_SQL.setName("SELECT_SQL");
		// SELECT_SQL.buildPositions();
		oraclesql.getInsertParams().add(SELECT_SQL);

		SQLParam INSERT_SQL = new SQLParam();
		INSERT_SQL.setDtype("2005");
		INSERT_SQL.setDtypename("CLOB");
		INSERT_SQL.setIndex("14,31");
		INSERT_SQL.setIotype(SQLParam.ParamType.in.name());
		INSERT_SQL.setName("INSERT_SQL");
		// INSERT_SQL.buildPositions();
		oraclesql.getInsertParams().add(INSERT_SQL);

		SQLParam UPDATE_SQL = new SQLParam();
		UPDATE_SQL.setDtype("2005");
		UPDATE_SQL.setDtypename("CLOB");
		UPDATE_SQL.setIndex("15,32");
		UPDATE_SQL.setIotype(SQLParam.ParamType.in.name());
		UPDATE_SQL.setName("UPDATE_SQL");
		// UPDATE_SQL.buildPositions();
		oraclesql.getInsertParams().add(UPDATE_SQL);

		SQLParam DELETE_SQL = new SQLParam();
		DELETE_SQL.setDtype("2005");
		DELETE_SQL.setDtypename("CLOB");
		DELETE_SQL.setIndex("16,33");
		DELETE_SQL.setIotype(SQLParam.ParamType.in.name());
		DELETE_SQL.setName("DELETE_SQL");
		// DELETE_SQL.buildPositions();
		oraclesql.getInsertParams().add(DELETE_SQL);

		SQLParam SQL_OPTIONS = new SQLParam();
		SQL_OPTIONS.setDtype("2");
		SQL_OPTIONS.setDtypename("NUMBER");
		SQL_OPTIONS.setIndex("17,34");
		SQL_OPTIONS.setIotype(SQLParam.ParamType.in.name());
		SQL_OPTIONS.setName("SQL_OPTIONS");
		// SQL_OPTIONS.buildPositions();
		oraclesql.getInsertParams().add(SQL_OPTIONS);

		SQLParam TABLENAME = new SQLParam();
		TABLENAME.setDtype("12");
		TABLENAME.setDtypename("VARCHAR2");
		TABLENAME.setIndex("18,35");
		TABLENAME.setIotype(SQLParam.ParamType.in.name());
		TABLENAME.setName("TABLENAME");
		// TABLENAME.buildPositions();
		oraclesql.getInsertParams().add(TABLENAME);

		SQLParam keyField = new SQLParam();
		keyField.setDtype("12");
		keyField.setDtypename("VARCHAR2");
		keyField.setIndex("19,36");
		keyField.setIotype(SQLParam.ParamType.in.name());
		keyField.setName("KEYFIELD");
		// keyField.buildPositions();
		oraclesql.getInsertParams().add(keyField);
	}

	private SQLObjectManager() {
		orasqlcache = CacheFactory.getCache(sqlCacheName);
		lock = new ReentrantReadWriteLock();
		readLock = lock.readLock();
		writeLock = lock.writeLock();
		oraclesql = new SQLObject();
		buildOracleSQLObject();
	}

	public SQLObject getSQLObject(ConnectionPool.DatabaseType databasetype) {
		SQLObject result = null;
		switch (databasetype) {
		case ORACLE: {
			result = oraclesql;
			break;
		}
		case MYSQL: {
			break;
		}
		case SQLSERVER: {
			break;
		}
		case DB2: {
			break;
		}
		}
		return result;
	}

	public static SQLObjectManager getInstance() {
		if (instance == null) {
			instance = new SQLObjectManager();
		}

		return instance;
	}

	private SQLObject getSQlObjectByMap(Map<String, Object> data) {
		SQLObject result = new SQLObject();
		result.setSqlId((String) data.get("oid"));
		result.setPid((String) data.get("pid"));
		result.setMid((String) data.get("mid"));
		result.setSqlId((String) data.get("sqlid"));
		if(data.get("sql_options") instanceof Long){
			result.setSqlOptions((Long) data.get("sql_options"));
		}else if(data.get("sql_options") instanceof String){
			result.setSqlOptions(Long.valueOf((String)data.get("sql_options")) );
		}
		result.setSqlName((String) data.get("sqlname"));
		result.setTableName((String) data.get("tablename"));
		result.setSqlDesc((String) data.get("sqldesc"));
		result.setDefSelectSql((String) data.get("def_select_sql"));
		result.setDefselectparams((String) data.get("def_select_params"));
		result.setDefselectmetadata((String) data.get("def_select_metadata"));
		result.setDefInsertSql((String) data.get("def_insert_sql"));
		result.setDefinsertparams((String) data.get("def_insert_params"));
		result.setDefUpdateSql((String) data.get("def_update_sql"));
		result.setDefupdateparams((String) data.get("def_update_params"));
		result.setDefDeleteSql((String) data.get("def_delete_sql"));
		result.setDefdeleteparams((String) data.get("def_delete_params"));
		result.setSelectSql((String) data.get("select_sql"));
		result.setInsertSql((String) data.get("insert_sql"));
		result.setUpdateSql((String) data.get("update_sql"));
		result.setDeleteSql((String) data.get("delete_sql"));
		result.setUpdateDate((Date) data.get("update_date"));
		if(data.get("obj_version") instanceof Long){
			result.setObjectVersion((Long) data.get("obj_version"));
		}else if(data.get("obj_version") instanceof String){
			result.setObjectVersion(Long.valueOf((Integer)data.get("obj_version")) );
		}
		result.setKeyField((String) data.get("keyfield"));
		result.setDefSingleSelectSql((String)data.get("def_singleselect_sql"));
		result.setSingleSelectSql((String)data.get("singleselect_sql"));
		result.setDefSingleSelectParams((String)data.get("def_singleselect_params"));
		// result.buildParamAndMeta();
		return result;
	}

	public SQLObject getSQLObjectByCache(String sqlid) {
		SQLObject result = null;
		switch (DataServer.getInstance().getProperties().getDbtype()) {
		case ORACLE: {
			result = orasqlcache.get(sqlid);
			break;
		}
		case MYSQL: {
			break;
		}
		case SQLSERVER: {
			break;
		}
		case DB2: {
			break;
		}
		}

		return result;

	}

	public void setSqlObjectByCache(String sqlid, SQLObject sqlobj) {
		switch (DataServer.getInstance().getProperties().getDbtype()) {
		case ORACLE: {
			orasqlcache.put(sqlid, sqlobj);
			break;
		}
		case MYSQL: {
			break;
		}
		case SQLSERVER: {
			break;
		}
		case DB2: {
			break;
		}
		}
	}

	public SQLObject getSQLObjectByDB(String sqlid) throws Exception {
		SQLObject result = null;
		Connection conn = DataServer.getInstance().getPool().getConnection();
		try {
			Connection nativeconn = DataServer.getInstance().getPool()
					.getNativeConnection(conn);
			try {
				DataExecutor executor = DataExecutorFactory
						.getExecutor(DataServer.getInstance().getProperties()
								.getDbtype());

				List<SQLParam> params = new ArrayList<SQLParam>();
				SQLParam param = null;

				List<Map<String, Object>> datas = null;
				switch (DataServer.getInstance().getProperties().getDbtype()) {
				case ORACLE: {
					param = new SQLParam();
					param.setDtype(String.valueOf(OracleTypes.VARCHAR));
					param.setIotype(SQLParam.ParamType.in.name());
					param.setIndex("1");
					param.setValue(sqlid);
					// param.buildPositions();
					params.add(param);
					datas = executor.queryData(OracleSql.SELECT_SQL, false,
							params, nativeconn);
					break;
				}

				case MYSQL: {
					datas = executor.queryData("{? = call getSQLObject(?)}",
							true, params, nativeconn);
					break;
				}
				case SQLSERVER: {
					datas = executor.queryData("{? = call getSQLObject(?)}",
							true, params, nativeconn);
					break;
				}
				case DB2: {
					datas = executor.queryData("{? = call getSQLObject(?)}",
							true, params, nativeconn);
					break;
				}
				}

				if (datas.size() <= 0) {
					throw new Exception("数据库中不存在SQLID为：" + sqlid + " 的对象，请检查!");
				} else {
					Map<String, Object> data = datas.get(0);

					result = this.getSQlObjectByMap(data);
					this.setSqlObjectByCache(sqlid, result);
				}
				nativeconn.commit();
			} catch (Exception e) {
				nativeconn.rollback();
				throw e;
			}
		} finally {
			conn.close();
			conn = null;
		}

		return result;
	}

	public SQLObject getSQLObjectById(String sqlid) throws Exception {
		readLock.lock();
		try {
			SQLObject result = this.getSQLObjectByCache(sqlid);
			if (result == null) {
				synchronized (sqlid.intern()) {
					result = this.getSQLObjectByCache(sqlid);
					if (result == null) {
						result = getSQLObjectByDB(sqlid);
					}
				}
			}

			return result;
		} finally {
			readLock.unlock();
		}
	}

	public List<Map<String, Object>> getAllSQLObjectForMap() throws Exception {
		List<Map<String, Object>> result = null;
		Connection conn = DataServer.getInstance().getPool().getConnection();
		try {
			Connection nativeconn = DataServer.getInstance().getPool()
					.getNativeConnection(conn);
			try {
				DataExecutor executor = DataExecutorFactory
						.getExecutor(DataServer.getInstance().getProperties()
								.getDbtype());

				switch (DataServer.getInstance().getProperties().getDbtype()) {
				case ORACLE: {
					result = executor.queryData(OracleSql.SELECT_ALL_SQL,
							false, null, nativeconn);
					break;
				}
				case MYSQL: {
					result = executor.queryData(
							"{? = call PKG_SQLObject.getAllSQLObject }", true,
							null, nativeconn);
					break;
				}
				case SQLSERVER: {
					result = executor.queryData(
							"{? = PKG_SQLObject.getAllSQLObject }", true, null,
							nativeconn);
					break;
				}
				case DB2: {
					result = executor.queryData(
							"{? = PKG_SQLObject.getAllSQLObject }", true, null,
							nativeconn);
					break;
				}
				}
				nativeconn.commit();
			} catch (Exception e) {
				nativeconn.rollback();
				throw e;
			}
		} finally {
			conn.close();
			conn = null;
		}
		return result;
	}

	public List<SQLObject> getAllSQLObject() throws Exception {
		List<SQLObject> results = new ArrayList<SQLObject>();
		List<Map<String, Object>> list = getAllSQLObjectForMap();
		Iterator<Map<String, Object>> iter = list.iterator();
		Map<String, Object> data = null;
		SQLObject result = null;
		writeLock.lock();
		try {
			while (iter.hasNext()) {
				data = iter.next();

				result = this.getSQlObjectByMap(data);
				this.setSqlObjectByCache(result.getSqlId(), result);

				results.add(result);
			}
		} finally {
			writeLock.unlock();
		}
		return results;
	}

	public Map<String, Object> saveSQLObjectBySqlid(String sqlid, String sql,
			List<SQLParam> params) throws Exception {
		Map<String, Object> result = null;
		result = this.saveSQLObject(sql, params);
		synchronized (sqlid.intern()) {
			this.getSQLObjectByDB(sqlid);
		}
		return result;
	}

	public Map<String, Object> saveSQLObject(String sql, List<SQLParam> params)
			throws Exception {
		Map<String, Object> result = null;
		Connection conn = DataServer.getInstance().getPool().getConnection();
		try {
			Connection nativeconn = DataServer.getInstance().getPool()
					.getNativeConnection(conn);
			try {
				DataExecutor executor = DataExecutorFactory
						.getExecutor(DataServer.getInstance().getProperties()
								.getDbtype());

				result = executor.saveData(sql, params, nativeconn);
				nativeconn.commit();
			} catch (Exception e) {
				nativeconn.rollback();
				throw e;
			}
		} finally {
			conn.close();
			conn = null;
		}

		return result;
	}

	public List<MetaData> queryMetaData(String sql, boolean isCursor)
			throws Exception {
		List<MetaData> results = null;

		Connection conn = DataServer.getInstance().getPool().getConnection();
		try {
			Connection nativeconn = DataServer.getInstance().getPool()
					.getNativeConnection(conn);
			try {
				DataExecutor executor = DataExecutorFactory
						.getExecutor(DataServer.getInstance().getProperties()
								.getDbtype());

				results = executor.queryMetaData(sql, isCursor, nativeconn);

				nativeconn.commit();
			} catch (Exception e) {
				nativeconn.rollback();
				throw e;
			}
		} finally {
			conn.close();
			conn = null;
		}
		return results;
	}

	public Map<String, Object> queryParams(String sql,
			Map<String, Object> results) throws TechException {
		try {
			Connection conn = DataServer.getInstance().getPool()
					.getConnection();
			try {
				Connection nativeconn = DataServer.getInstance().getPool()
						.getNativeConnection(conn);
				try {
					DataExecutor executor = DataExecutorFactory
							.getExecutor(DataServer.getInstance()
									.getProperties().getDbtype());

					Map<String, Object> tempresults = executor.queryParams(sql,
							nativeconn);
					nativeconn.commit();
					results.put("datas", tempresults);
				} catch (Exception e) {
					nativeconn.rollback();
					throw e;
				}
			} finally {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			throw new TechException(e.getClass().getName() + " \n"
					+ e.getMessage());
		}

		return results;
	}

	public Map<String, String> getTypeMap() {
		Map<String, String> result = new HashMap<String, String>();

		return result;
	}
	/**
	 * 加载sql语句缓存
	 * @param sqlid
	 * @param sqlMap
	 */
	public  void  loadSqlCache(String sqlid,Map<String, Object> data){
		if(SQLObjectManager.sqlCacheName.equals(sqlid)){
			if(!data.containsKey("sqlid")){
				return;
			}
			SQLObject result =  getSQlObjectByMap(data);
			setSqlObjectByCache(data.get("sqlid").toString(), result);
		}
	}
	public static void main(String args[]) throws Exception {
		new DataServer(new File(
				"E:/toos/apache-tomcat-7.0.4/wtpwebapps/IXWebService")).start();

		SQLObject sql = SQLObjectManager.getInstance().getAllSQLObject().get(0);

		System.out.println(sql.getTableName());

		System.out.println(sql.getDefupdateparams());
	}
	
}
