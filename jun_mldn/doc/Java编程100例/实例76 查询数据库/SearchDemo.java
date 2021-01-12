import java.sql.*;

public class SearchDemo{
	private String dbURL="jdbc:microsoft:sqlserver://202.115.26.181:1433";	// 数据库标识名
	private String user="devon";	// 数据库用户
	private String password="book";	// 数据库用户密码
	
	public SearchDemo(){
		try	{
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");  //加载驱动器
			Connection con=DriverManager.getConnection(dbURL,user,password); //获取连接
			String sqlStr="select * from users where age>20"; //SQL查询语句
			Statement st=con.createStatement(); //获取Statement对象
			ResultSet rs=st.executeQuery(sqlStr); //执行查询
			String name,sex,email; //查询结果
			int age;
			while (rs.next()){ //遍历ResultSet
				name=rs.getString("name"); //获取数据
				age=rs.getInt("age");
				sex=rs.getString("sex");
				email=rs.getString("email");
				System.out.println("Name: "+name+"\tAge:"+age+"\tSex:"+sex+"\tEmail:"+email); //在控制台输出数据
			}			

			con.close(); //关闭连接
		}
		catch(Exception ex){
			ex.printStackTrace(); //输出出错信息
		}
	}
	
	public static void main(String[] args){
		new SearchDemo();
	}
}