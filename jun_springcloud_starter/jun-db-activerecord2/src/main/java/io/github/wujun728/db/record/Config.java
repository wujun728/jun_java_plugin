/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wujun728.db.record;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import io.github.wujun728.db.record.dialect.Dialect;
import io.github.wujun728.db.record.dialect.MysqlDialect;
import io.github.wujun728.db.record.kit.LogKit;
import io.github.wujun728.db.record.kit.StrKit;

public class Config {
	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	String name;
	DataSource dataSource;
	
	Dialect dialect;
	boolean showSql;
	boolean devMode;
	int transactionLevel;
	IContainerFactory containerFactory = IContainerFactory.defaultContainerFactory;
	IDbProFactory dbProFactory = IDbProFactory.defaultDbProFactory;
//	ICache cache;
	
//	SqlKit sqlKit;
	
	// For ActiveRecordPlugin only, dataSource can be null
	public Config(String name, DataSource dataSource, int transactionLevel) {
		init(name, dataSource, new MysqlDialect(), false, false, transactionLevel, IContainerFactory.defaultContainerFactory/*, new EhCache()*/);
	}
	
	/**
	 * Constructor with full parameters
	 * @param name the name of the config
	 * @param dataSource the dataSource
	 * @param dialect the dialect
	 * @param showSql the showSql
	 * @param devMode the devMode
	 * @param transactionLevel the transaction level
	 * @param containerFactory the containerFactory
	 */
	public Config(String name, DataSource dataSource, Dialect dialect, boolean showSql, boolean devMode, int transactionLevel, IContainerFactory containerFactory/*, ICache cache*/) {
		if (dataSource == null) {
			throw new IllegalArgumentException("DataSource can not be null");
		}
		init(name, dataSource, dialect, showSql, devMode, transactionLevel, containerFactory/*, cache*/);
	}
	
	private void init(String name, DataSource dataSource, Dialect dialect, boolean showSql, boolean devMode, int transactionLevel, IContainerFactory containerFactory/*, ICache cache*/) {
		if (StrKit.isBlank(name)) {
			throw new IllegalArgumentException("Config name can not be blank");
		}
		if (dialect == null) {
			throw new IllegalArgumentException("Dialect can not be null");
		}
		if (containerFactory == null) {
			throw new IllegalArgumentException("ContainerFactory can not be null");
		}
		this.name = name.trim();
		this.dataSource = dataSource;
		this.dialect = dialect;
		this.showSql = showSql;
		this.devMode = devMode;
		// this.transactionLevel = transactionLevel;
		this.setTransactionLevel(transactionLevel);
		this.containerFactory = containerFactory;
//		this.cache = cache;
//		this.sqlKit = new SqlKit(this.name, this.devMode);
	}
	
	/**
	 * Constructor with name and dataSource
	 */
	public Config(String name, DataSource dataSource) {
		this(name, dataSource, new MysqlDialect());
	}
	
	/**
	 * Constructor with name, dataSource and dialect
	 */
	public Config(String name, DataSource dataSource, Dialect dialect) {
		this(name, dataSource, dialect, false, false, DbKit.DEFAULT_TRANSACTION_LEVEL, IContainerFactory.defaultContainerFactory/*, new EhCache()*/);
	}
	
	private Config() {
		
	}

	/**
	 * Constructor with full parameters
	 * @param dataSource the dataSource, can not be null
	 * @param dialect the dialect, set null with default value: new MysqlDialect()
	 * @param showSql the showSql,set null with default value: false
//	 * @param devMode the devMode, set null with default value: false
	 * @param transactionLevel the transaction level, set null with default value: Connection.TRANSACTION_READ_COMMITTED
//	 * @param cache the cache, set null with default value: new EhCache()
	 */
	public Config(String name,
				  DataSource dataSource,
				  Dialect dialect,
				  Boolean showSql,
				  Integer transactionLevel) {
		if (StrKit.isBlank(name))
			throw new IllegalArgumentException("Config name can not be blank");
		if (dataSource == null)
			throw new IllegalArgumentException("DataSource can not be null");

		this.name = name.trim();
		this.dataSource = dataSource;

		if (dialect != null)
			this.dialect = dialect;
		if (showSql != null)
			this.showSql = showSql;
		if (transactionLevel != null)
			this.transactionLevel = transactionLevel;
	}
	
	void setDevMode(boolean devMode) {
		this.devMode = devMode;
//		this.sqlKit.setDevMode(devMode);
	}
	
	void setTransactionLevel(int transactionLevel) {
		int t = transactionLevel;
		if (t != 0 && t != 1  && t != 2  && t != 4  && t != 8) {
			throw new IllegalArgumentException("The transactionLevel only be 0, 1, 2, 4, 8");
		}
		this.transactionLevel = transactionLevel;
	}
	
	/**
	 * Create broken config for DbKit.brokenConfig = Config.createBrokenConfig();
	 */
	static Config createBrokenConfig() {
		Config ret = new Config();
		ret.dialect = new MysqlDialect();
		ret.showSql = false;
		ret.devMode = false;
		ret.transactionLevel = DbKit.DEFAULT_TRANSACTION_LEVEL;
		ret.containerFactory = IContainerFactory.defaultContainerFactory;
//		ret.cache = new EhCache();
		return ret;
	}
	
	public String getName() {
		return name;
	}
	
//	public SqlKit getSqlKit() {
//		return sqlKit;
//	}
	
	public Dialect getDialect() {
		return dialect;
	}
	
//	public ICache getCache() {
//		return cache;
//	}
	
	public int getTransactionLevel() {
		return transactionLevel;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public IContainerFactory getContainerFactory() {
		return containerFactory;
	}
	
	public IDbProFactory getDbProFactory() {
		return dbProFactory;
	}
	
	public boolean isShowSql() {
		return showSql;
	}
	
	public boolean isDevMode() {
		return devMode;
	}
	
	// --------
	
	/**
	 * Support transaction with Transaction interceptor
	 */
	public void setThreadLocalConnection(Connection connection) {
		threadLocal.set(connection);
	}
	
	public void removeThreadLocalConnection() {
		threadLocal.remove();
	}
	
	/**
	 * Get Connection. Support transaction if Connection in ThreadLocal
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if (conn != null)
			return conn;
		return showSql ? new SqlReporter(dataSource.getConnection()).getConnection() : dataSource.getConnection();
	}
	
	/**
	 * Helps to implement nested transaction.
	 * Tx.intercept(...) and Db.tx(...) need this method to detected if it in nested transaction.
	 */
	public Connection getThreadLocalConnection() {
		return threadLocal.get();
	}
	
	/**
	 * Return true if current thread in transaction.
	 */
	public boolean isInTransaction() {
		return threadLocal.get() != null;
	}
	
	/**
	 * Close ResultSet、Statement、Connection
	 * ThreadLocal support declare transaction.
	 */
	public void close(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {try {rs.close();} catch (SQLException e) {LogKit.error(e.getMessage(), e);}}
		if (st != null) {try {st.close();} catch (SQLException e) {
			LogKit.error(e.getMessage(), e);}}
		
		if (threadLocal.get() == null) {	// in transaction if conn in threadlocal
			if (conn != null) {try {conn.close();}
			catch (SQLException e) {throw new ActiveRecordException(e);}}
		}
	}
	
	public void close(Statement st, Connection conn) {
		if (st != null) {try {st.close();} catch (SQLException e) {LogKit.error(e.getMessage(), e);}}
		
		if (threadLocal.get() == null) {	// in transaction if conn in threadlocal
			if (conn != null) {try {conn.close();}
			catch (SQLException e) {throw new ActiveRecordException(e);}}
		}
	}
	
	public void close(Connection conn) {
		if (threadLocal.get() == null)		// in transaction if conn in threadlocal
			if (conn != null)
				try {conn.close();} catch (SQLException e) {throw new ActiveRecordException(e);}
	}
}



