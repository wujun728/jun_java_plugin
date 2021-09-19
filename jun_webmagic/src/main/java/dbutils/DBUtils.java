package dbutils;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
* @ClassName: JdbcUtils2
* @Description: 数据库连接工具类
* @author: 孤傲苍狼
* @date: 2014-10-4 下午6:04:36
*
*/ 
public class DBUtils {
    
//    private static ComboPooledDataSource ds = null;
    private static DruidDataSource ds = null;
    //使用ThreadLocal存储当前线程中的Connection对象
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    
    //在静态代码块中创建数据库连接池
    static{
        try{
        	
            //通过代码创建C3P0数据库连接池
            /*ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.jdbc.Driver");
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/jdbcstudy");
            ds.setUser("root");
            ds.setPassword("XDP");
            ds.setInitialPoolSize(10);
            ds.setMinPoolSize(5);
            ds.setMaxPoolSize(20);*/
            
            //通过读取C3P0的xml配置文件创建数据源，C3P0的xml配置文件c3p0-config.xml必须放在src目录下
            //ds = new ComboPooledDataSource();//使用C3P0的默认配置来创建数据源
//            ds = new ComboPooledDataSource("MySQL");//使用C3P0的命名配置来创建数据源
//        	Properties properties = loadPropertiesFile("druid.properties");
        	
//        	FileInputStream fis = new FileInputStream(JdbcUtils.class.getResource("/").getPath() + "druid.properties");
//        	Properties p = new Properties();
//        	p.load(fis);
        	Properties prop = new Properties();
        	prop.load(DBUtils.class.getClass().getResourceAsStream("/druid.properties"));
            ds = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop); // DruidDataSrouce工厂模式
            
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static void main(String[] args) throws SQLException {
		System.err.println(DBUtils.getConnection());
	}
    
    /**
	 * @param string 配置文件名
	 * @return Properties对象
	 */
	private static Properties loadPropertiesFile(String fullFile) {
		String webRootPath = null;
		if (null == fullFile || fullFile.equals("")) {
			throw new IllegalArgumentException("Properties file path can not be null" + fullFile);
		}
		webRootPath = DBUtils.class.getClassLoader().getResource("").getPath();
		webRootPath = new File(webRootPath).getParent();
		InputStream inputStream = null;
		Properties p = null;
		try {
			inputStream = new FileInputStream(new File(webRootPath + File.separator + fullFile));
			p = new Properties();
			p.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return p;
	}
    
    /**
    * @Method: getConnection
    * @Description: 从数据源中获取数据库连接
    * @Anthor:孤傲苍狼
    * @return Connection
    * @throws SQLException
    */ 
    public static Connection getConnection() throws SQLException{
        //从当前线程中获取Connection
        Connection conn = threadLocal.get();
        if(conn==null){
            //从数据源中获取数据库连接
            conn = getDataSource().getConnection();
            //将conn绑定到当前线程
            threadLocal.set(conn);
        }
        return conn;
    }
    
    /**
    * @Method: startTransaction
    * @Description: 开启事务
    * @Anthor:孤傲苍狼
    *
    */ 
    public static void startTransaction(){
        try{
            Connection conn =  threadLocal.get();
            if(conn==null){
                conn = getConnection();
                 //把 conn绑定到当前线程上
                threadLocal.set(conn);
            }
            //开启事务
            conn.setAutoCommit(false);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: rollback
    * @Description:回滚事务
    * @Anthor:孤傲苍狼
    *
    */ 
    public static void rollback(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //回滚事务
                conn.rollback();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: commit
    * @Description:提交事务
    * @Anthor:孤傲苍狼
    *
    */ 
    public static void commit(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                //提交事务
                conn.commit();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: close
    * @Description:关闭数据库连接(注意，并不是真的关闭，而是把连接还给数据库连接池)
    * @Anthor:孤傲苍狼
    *
    */ 
    public static void close(){
        try{
            //从当前线程中获取Connection
            Connection conn = threadLocal.get();
            if(conn!=null){
                conn.close();
                 //解除当前线程上绑定conn
                threadLocal.remove();
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * @Method: getDataSource
    * @Description: 获取数据源
    * @Anthor:孤傲苍狼
    * @return DataSource
    */ 
    public static DataSource getDataSource(){
        //从数据源中获取数据库连接
        return ds;
    }
}