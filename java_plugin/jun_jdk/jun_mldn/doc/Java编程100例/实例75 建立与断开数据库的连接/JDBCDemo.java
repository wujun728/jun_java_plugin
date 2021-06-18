import java.sql.*;

public class JDBCDemo{
	private String dbURL="jdbc:microsoft:sqlserver://202.115.26.181:1433";	// 数据库标识名
	private String user="devon";	// 数据库用户名
	private String password="book";		// 数据库用户密码
	
	public JDBCDemo(){
		try	{
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");  //加载驱动器
			Connection con=DriverManager.getConnection(dbURL,user,password); //获取连接
			DatabaseMetaData dbmd=con.getMetaData(); //获取DatabaseMetaData实例
			
			System.out.println(dbmd.getDatabaseProductName()); //获取数据库名称
			System.out.println(dbmd.getDatabaseProductVersion());  //获取数据库版本号
			System.out.println(dbmd.getDriverName());  //获取JDBC驱动器名称
			System.out.println(dbmd.getDriverVersion());  //获取驱动器版本号
			System.out.println(dbmd.getUserName());	 //获取登录用户名

			con.close();  //关闭连接
		}
		catch(Exception ex)	{
			ex.printStackTrace(); //输出出错信息
		}
	}
	
	public static void main(String[] args){
		new JDBCDemo();
	}
}
