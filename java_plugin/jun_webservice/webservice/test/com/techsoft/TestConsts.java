package com.techsoft;

import com.techsoft.pool.PoolFactory;

public class TestConsts {
	private static ConnectionPool pool = null;
	public static ConnectionPool.DatabaseType databaseType = ConnectionPool.DatabaseType.ORACLE;

	public static String driverName = "oracle.jdbc.driver.OracleDriver";

	public static int minPool = 1;

	public static int MaxPool = 2;

	public static int initPool = 1;

	public static String URL = "jdbc:oracle:thin:@192.168.0.48:1521/ORA10G.localdomain";

	public static String userName = "samp";

	public static String password = "lxkj";

	public static ConnectionPool getPool() {
		if (pool == null) {
			try {
				pool = PoolFactory.getInstance().getConnectionPool();
				pool.setDatabaseType(TestConsts.databaseType);
				pool.setDriverName(TestConsts.driverName);
				pool.setInitPool(TestConsts.initPool);
				pool.setIsDebug(true);
				pool.setJdbcURL(TestConsts.URL);
				pool.setMaxPool(TestConsts.MaxPool);
				pool.setMinPool(TestConsts.minPool);
				pool.setUserName(TestConsts.userName);
				pool.setPassword(TestConsts.password);
				pool.start();
			} catch (PoolException e) {
				e.printStackTrace();
			}
		}

		return pool;

	}

}
