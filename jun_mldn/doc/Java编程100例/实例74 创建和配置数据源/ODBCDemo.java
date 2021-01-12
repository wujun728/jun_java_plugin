import java.sql.*;

public class ODBCDemo{
	private String dbURL="jdbc:odbc:example";	//数据库标识名
	private String user="devon";	//数据库用户
	private String password="book";	//数据库用户密码
	
	public ODBCDemo(){
		try	{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	//装载数据库驱动
			Connection con=DriverManager.getConnection(dbURL,user,password); //得到连接
			System.out.println(con.getCatalog());   //打印当前数据库目录名称
			System.out.println("连接成功");  
			con.close(); //关闭连接			
		}
		catch (Exception ex)	{
			ex.printStackTrace();  //输出出错信息
		}
	}
	
	public static void main(String args[]){
		new ODBCDemo();
	}
}