package com.jun.base.datasource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * 需要在类路径下准备数据库连接配置文件hikari.properties
 */
public class DataSourceHikariCP {
	private static final String configFile = "hikari.properties";
	private static DataSource dataSource;
	//private static Properties properties = loadPropertyFile(configFile);
	static {
		try {
			
			HikariConfig config = new HikariConfig(DataSourceDruid.class.getClassLoader().getResource("").getPath() + File.separator + configFile);
			dataSource = new HikariDataSource(config);
			
			Connection conn = getConn();
			DatabaseMetaData mdm = conn.getMetaData();
			System.err.println("Connected to " + mdm.getDatabaseProductName() + " " +mdm.getDatabaseProductVersion() + " " + DataSourceHikariCP.getConn());
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.err.println("初始化连接池失败：" + e);
		}
	}

	private DataSourceHikariCP() {
	}

	/**
	 * 获取链接，用完后记得关闭
	 * 
	 * @see {@link DBManager#closeConn(Connection)}
	 * @return
	 */
	public static final Connection getConn() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// log.error("获取数据库连接失败：" + e);
		}
		return conn;
	}
	public static final DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 *            需要关闭的连接
	 */
	public static void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			// log.error("关闭数据库连接失败：" + e);
		}
	}
	
    
    public static void closeAll(ResultSet rs,Statement stmt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try {
                conn.close();//关闭
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
	 * 
	 * @param fullFile
	 * @return
	 */
	private static Properties loadPropertyFile(String fullFile) {
		String webRootPath = null;
		if (null == fullFile || fullFile.equals(""))
			throw new IllegalArgumentException("Properties file path can not be null : " + fullFile);
		webRootPath = DataSourceHikariCP.class.getClassLoader().getResource("").getPath();
		InputStream inputStream = null;
		Properties p = null;
		try {
			inputStream = new FileInputStream(new File(webRootPath + File.separator + fullFile));
			p = new Properties();
			p.load(inputStream);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found: " + fullFile);
		} catch (IOException e) {
			throw new IllegalArgumentException("Properties file can not be loading: " + fullFile);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
	
	public static void main(String[] args) throws SQLException {
		System.err.println(DataSourceHikariCP.getConn());
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", "49.235.86.47");
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", "test");
		config.addDataSourceProperty("user", "root");
		config.addDataSourceProperty("password", "mysqladmin");
		HikariDataSource ds = new HikariDataSource(config);
		System.err.println(ds.getConnection());
	}
}