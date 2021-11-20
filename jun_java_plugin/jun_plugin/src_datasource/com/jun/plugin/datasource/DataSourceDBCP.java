package com.jun.plugin.datasource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
import org.junit.Test;


/**
 * 需要在类路径下准备数据库连接配置文件dbcp.properties
 */
public class DataSourceDBCP {
	private static final String configFile = "dbcp.properties";
	private static DataSource dataSource;
	static {
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(DataSourceDBCP.class.getClassLoader().getResourceAsStream(configFile));
			dataSource = BasicDataSourceFactory.createDataSource(dbProperties);
			Connection conn = getConn();
			DatabaseMetaData mdm = conn.getMetaData();
			 System.err.println("Connected to " + mdm.getDatabaseProductName() + " " +mdm.getDatabaseProductVersion());
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.err.println("初始化连接池失败：" + e);
		}
	}

	private DataSourceDBCP() {
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
	
	
	public static void main(String[] args) {
		System.err.println(DataSourceDBCP.getConn());
	}
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################
	//###############################################################################################################

	/**
	 * 通过配置文件创建连接
	 */
	public void testpool11() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		// 设置driver
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 设置url
		ds.setUrl("jdbc:mysql:///test?characterEncoding=UTf8");
		ds.setUsername("root");
		ds.setPassword("mysqladmin");
		ds.setMaxActive(5);// 设置最多有几个连接
		ds.setInitialSize(2);// 设置在开始时创建几个连接
		// ..................
		ds.setDefaultAutoCommit(true);// 设置所有连接是否自动提交
		ds.setMaxIdle(3000);// 设置每个连接最大的空闲时间
		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();
		Connection c4 = ds.getConnection();
		Connection c5 = ds.getConnection();
		System.err.println("c1:" + c1.hashCode() + "," + c1.getClass());// com.mysql.jdbc.Jdbc4Connection@11111,
		System.err.println("c2:" + c2.hashCode());// cn.itcast.MyDataSource$MyConn@11111
		System.err.println("c3:" + c3.hashCode());
		System.err.println("c4:" + c4.hashCode());
		System.err.println("c5:" + c5.hashCode());
		c2.close();
		Connection c6 = ds.getConnection();
		System.err.println("c6:" + c6.hashCode());
	}

	@SuppressWarnings("static-access")
	// @Test
	public void Test3() throws Exception {
		long begin = System.currentTimeMillis();
		InputStream is = DataSourceDBCP.class.getClassLoader().getResourceAsStream("dbcp.properties");
		Properties props = new Properties();
		props.load(is);
		BasicDataSourceFactory factory = new BasicDataSourceFactory();
		DataSource ds = factory.createDataSource(props);
		for (int i = 1; i <= 50000; i++) {
			Connection conn = ds.getConnection();
			if (conn != null) {
				System.out.println(i + ":ȡ������");
			}
			conn.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("����" + (end - begin) / 1000 + "��");
	}

	// @Test
	public void testpool1() throws Exception {
		BasicDataSource ds = new BasicDataSource();
		// 设置driver
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 设置url
		ds.setUrl("jdbc:mysql:///db909?characterEncoding=UTf8");
		ds.setPassword("1234");
		ds.setUsername("root");
		ds.setMaxActive(5);// 设置最多有几个连接
		ds.setInitialSize(2);// 设置在开始时创建几个连接
		// ..................
		ds.setDefaultAutoCommit(true);// 设置所有连接是否自动提交
		ds.setMaxIdle(3000);// 设置每个连接最大的空闲时间

		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();
		Connection c4 = ds.getConnection();
		Connection c5 = ds.getConnection();
		System.err.println("c1:" + c1.hashCode() + "," + c1.getClass());// com.mysql.jdbc.Jdbc4Connection@11111,
		System.err.println("c2:" + c2.hashCode());// cn.itcast.MyDataSource$MyConn@11111
		System.err.println("c3:" + c3.hashCode());
		System.err.println("c4:" + c4.hashCode());
		System.err.println("c5:" + c5.hashCode());
		c2.close();
		Connection c6 = ds.getConnection();
		System.err.println("c6:" + c6.hashCode());
	}

	/**
	 * 通过配置文件创建连接
	 */
	@Test
	public void testPool2() throws Exception {
		Properties p = new Properties();
		p.load(DataSourceDBCP.class.getResourceAsStream("jdbc.properties"));
		DataSource ds = new BasicDataSourceFactory().createDataSource(p);
		Connection c1 = ds.getConnection();
		Connection c2 = ds.getConnection();
		Connection c3 = ds.getConnection();

		System.err.println(c1.hashCode() + "," + c1.getClass());
		System.err.println("c2:" + c2.hashCode());
		System.err.println("c3:" + c3.hashCode());
		c3.close();
		Connection c4 = ds.getConnection();
		System.err.println("c4:" + c4.hashCode());
	}
 
	
	

}