package com.jun.base.jdbc2.test;
import java.io.BufferedInputStream;  
import java.io.FileInputStream;  
import java.io.InputStream;  
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.Properties;  
  
/** 
 * @author adam.胡升阳 
 *  
 */  
public class JdbcByPropertiesUtil {  
    private static String filePath = "jdbc.properties";   
    private static JdbcByPropertiesUtil instance = null;  
      
    public JdbcByPropertiesUtil() {  
        super();  
    }  
  
    /** 
     * 单例方式创建对象 
     * @return 
     */  
    public static JdbcByPropertiesUtil getInstance() {  
        if (instance == null) {  
            synchronized (JdbcByPropertiesUtil.class) {  
                if (instance == null) {  
                    instance = new JdbcByPropertiesUtil();  
                }  
            }  
        }  
        return instance;  
    }  
         
    /** 
     * 读取properties文件中 数据库连接信息 
     * @param filePath 
     * add 2012-4-17 
     */  
    public static Properties readPropertiesFile(){  
        String realFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+filePath;   
        Properties pros = new Properties();    
        try {    
            InputStream is = new BufferedInputStream(new FileInputStream(realFilePath));    
            pros.load(is);   
        } catch (Exception e) {    
            e.printStackTrace();  
        }  
        return pros;  
    }   
      
    /** 
     * 注册驱动 
     * 静态代码块 用于启动web服务器时加载驱动 
     */  
    static{  
        Properties pros = readPropertiesFile();  
        String className = (String) pros.get("className");  
        try {  
            Class.forName(className).newInstance();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
      
    /** 
     * 获取数据库连接 
     * modify 2012-4-17 
     * @param con 
     * @return 
     */  
    public Connection getConnection(){  
        Properties pros = readPropertiesFile();  
        String url = (String) pros.get("url");  
        String user = (String) pros.get("user");  
        String password = (String) pros.get("password");  
        Connection conn = null;  
        try {  
            conn = DriverManager.getConnection(url,user,password);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
         
    /** 
     *  依次关闭ResultSet、Statement、Connection 
     *  若对象不存在则创建一个空对象 
     * @param rs 
     * @param st 
     * @param pst 
     * @param conn 
     */  
    public void close(ResultSet rs,Statement st,Connection conn){  
        if(rs != null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally{  
                if(st != null){  
                    try {  
                        st.close();  
                    } catch (SQLException e) {  
                        e.printStackTrace();  
                    } finally{  
                        if(conn != null){  
                            try {  
                                conn.close();  
                            } catch (SQLException e) {  
                                e.printStackTrace();  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
  
  
    /** 
     * 新增、修改、删除、查询记录(也可以改为有结果集ResultSet返回的查询方法) 
     * @param sql 
     * @throws   
     */  
    public void execute(String sql){  
        JdbcByPropertiesUtil jbpu = getInstance();  
        Connection conn = null;  
        PreparedStatement pst = null;  
        try {  
            conn = jbpu.getConnection();  
            conn.setAutoCommit(false);  
            pst = conn.prepareStatement(sql);  
            pst.execute();  
            conn.commit();  
        } catch (Exception e) {  
            try {  
                conn.rollback();  
            } catch (SQLException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        } finally{  
            //Statement st = null;  
            ResultSet rs = null;  
            jbpu.close(rs, pst, conn);   
        }  
    }  
   
    /** 
     * 新增、修改、删除记录 
     * @param sql 
     * @throws   
     */  
    public void executeUpdate(String sql) {  
        JdbcByPropertiesUtil jbpu = getInstance();  
        Connection conn = null;  
        PreparedStatement pst = null;  
        try {  
            conn = jbpu.getConnection();  
            conn.setAutoCommit(false);  
            pst = conn.prepareStatement(sql);  
            pst.executeUpdate();  
            conn.commit();  
        } catch (Exception e) {  
            try {  
                conn.rollback();  
            } catch (SQLException e1) {  
                e1.printStackTrace();  
            }  
            e.printStackTrace();  
        } finally{  
            //Statement st = null;  
            ResultSet rs = null;  
            jbpu.close(rs, pst, conn);   
        }  
    }  
      
      
}  