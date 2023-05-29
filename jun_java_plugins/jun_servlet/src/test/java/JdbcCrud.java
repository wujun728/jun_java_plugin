

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.jun.plugin.jdbc.JdbcUtil;


public class JdbcCrud {
	
	/* 
	create table users(
	    id int primary key,
	    name varchar(40),
	    password varchar(40),
	    email varchar(60),
	    birthday date
	);
	*/
	public static void main(String[] args) {
		JdbcCrud jc=new JdbcCrud();
		System.err.println(jc.getConnection());
	}
	
	public static Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	private static String propFileName = "/jdbc.properties";
	private static Properties prop = new Properties();
	private static String driver = "com.mysql.jdbc.Driver";
	private static String dburl = "jdbc:mysql://127.0.0.1:3306/test?user=root&password=mysqladmin&useUnicode=true";

	public JdbcCrud() {
		try {
			//JdbcCrud.class.getClassLoader().getResourceAsStream(propFileName);
			InputStream in = getClass().getResourceAsStream(propFileName);
			prop.load(in); 
			driver = prop.getProperty("driver");
			dburl = prop.getProperty("url",
					"jdbc:mysql://127.0.0.1:3306/test?user=root&password=mysqladmin&useUnicode=true&characterEncoding=gbk");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(dburl);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		if (conn == null) {
			System.err.println("����: DbConnectionManager.getConnection() �����ݿ�����ʧ��.\r\n\r\n��������:"
					+ driver + "\r\n����λ��:" + dburl);
		}
		return conn;
	}
	
    /**
    * @Method: release
    * @Description: 释放资源，
    *     要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
    * @Anthor:孤傲苍狼
    *
    * @param conn
    * @param st
    * @param rs
    */ 
    public static void release(Connection conn,Statement st,ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //关闭Connection数据库连接对象
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

	public static int getRow(String sql) {
		int i = 0;
		conn = JdbcUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) FROM " + sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			if (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			i = 0;
		} finally {
			System.out.println("SELECT COUNT(*) FROM " + sql);
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
			}
		}
		return i;
	}

	public static boolean Delete(String sql) {
		boolean b = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = JdbcCrud.getConnection();
			ps = con.prepareStatement(sql);
			if (ps.executeUpdate() > 0) {
				b = true;
			} else {
				b = false;
			}
		} catch (SQLException e) {
			b = false;
			e.printStackTrace();
		} finally {
			System.out.println(ps + " Database.Delete() " + sql);
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				ps = null;
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				con = null;
			}
		}
		return b;
	}
	
	

    @Test
    public void insert(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            //获取一个数据库连接
            conn = JdbcCrud.getConnection();
            //通过conn对象获取负责执行SQL命令的Statement对象
            st = conn.createStatement();
            //要执行的SQL命令
            String sql = "insert into users(id,name,password,email,birthday) values(3,'白虎神皇','123','bhsh@sina.com','1980-09-09')";
            //执行插入操作，executeUpdate方法返回成功的条数
            int num = st.executeUpdate(sql);
            if(num>0){
                System.out.println("插入成功！！");
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //SQL执行完成之后释放相关资源
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void delete(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "delete from users where id=3";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if(num>0){
                System.out.println("删除成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void update(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "update users set name='孤傲苍狼',email='gacl@sina.com' where id=3";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if(num>0){
                System.out.println("更新成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void find(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "select * from users where id=3";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                System.out.println(rs.getString("name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List resultSetToList(ResultSet rs) throws java.sql.SQLException {   
        if (rs == null)   
            return Collections.EMPTY_LIST;   
        ResultSetMetaData md =  rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等   
        int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数   
        List list = new ArrayList();   
        Map rowData = new HashMap();   
        while (rs.next()) {   
         rowData = new HashMap(columnCount);   
         for (int i = 1; i <= columnCount; i++) {   
                 rowData.put(md.getColumnName(i), rs.getObject(i));   
         }   
         list.add(rowData);   
         System.out.println("list:" + list.toString());   
        }   
        return list;   
}  


    @Test
    public void insertLog(){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "insert into log(id,log_info,log_param,log_content,log_date) values(?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, 1);//id是int类型的
            st.setString(2, "日志信息1");
            st.setString(3, "日志参数1");
            st.setString(4, "日志内容1");
            st.setDate(5, new java.sql.Date(new Date().getTime()));
            //执行插入操作，executeUpdate方法返回成功的条数
            int num = st.executeUpdate();
            if(num>0){
                System.out.println("插入成功！！  insertLog");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void deleteLog(){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "delete from log where id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, 1);
            int num = st.executeUpdate();
            if(num>0){
                System.out.println("删除成功！！  deleteLog");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void updateLog(){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "update log set log_content=?,log_info=? ,log_date=?,log_param=?  where id=?";
            st = conn.prepareStatement(sql);
            st.setString(1, "content");
            st.setString(2, "info");
            st.setDate(3, new java.sql.Date(new Date().getTime()));
            st.setString(4, "param");
            st.setInt(5, 2);
            int num = st.executeUpdate();
            if(num>0){
                System.out.println("更新成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
    }
    
    @Test
    public void findLog(){
    	List ls=null;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcCrud.getConnection();
            String sql = "select * from log where id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, 11);
            rs = st.executeQuery();
            /*if(rs.next()){
                System.out.println(rs.getString("log_content"));
            }*/
            ls=resultSetToList(rs);
            System.err.println(ls);
            Iterator it = ls.iterator();   
            while(it.hasNext()) {   
                Map hm = (Map)it.next();   
                System.out.println(hm.get("字段名大写"));   
            }  
        }catch (Exception e) {
            
        }finally{
            JdbcCrud.release(conn, st, rs);
        }
//		return list;
    }
    
    
    public  static java.sql.Date getSqlDate() {
    	return new java.sql.Date(new Date().getTime());
	}
    public  static java.sql.Date getSqlDate(String date) {
    	java.sql.Date d = null;
    	try {
			d = new java.sql.Date((new SimpleDateFormat("yyyy-mm-dd").parse(date)).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return d;
    }
	 
}








