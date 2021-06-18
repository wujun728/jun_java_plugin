import java.sql.*;

public class UpdateDemo{
	private String dbURL="jdbc:odbc:example";	// 数据库标识名
	private String user="devon";	// 数据库用户
	private String password="book";	// 数据库用户密码
	
	public UpdateDemo(){
		try	{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  //加载驱动器
			Connection con=DriverManager.getConnection(dbURL,user,password); //获取连接
			String sqlStr="select * from users"; //SQL查询语句
			Statement st=con.createStatement(); //获取PreparedStatement对象
			ResultSet rs=st.executeQuery(sqlStr); //执行查询
			String name,sex,email; //查询结果
			int age;
			System.err.println("更新前数据");
			while (rs.next()){ //遍历ResultSet
				name=rs.getString("name"); //获取数据
				age=rs.getInt("age");
				sex=rs.getString("sex");
				email=rs.getString("email");
				System.out.println("Name: "+name+"\tAge:"+age+"\tSex:"+sex+"\tEmail:"+email); //在控制台输出数据
			}
			
			//更新操作
			sqlStr="update users set age=21 where name=\'John\'";
			st.executeUpdate(sqlStr);

			
			//删除操作
			sqlStr="delete from users where name=\'Jinnifier\'";
			st.executeUpdate(sqlStr);
			
			//插入操作
			sqlStr="insert into users(name,age,sex,email) values(\'Bush\',53,\'male\',\'bush@yahoo.com\')";
			st.executeUpdate(sqlStr);
			
			//显示修改后结果	
			System.err.println("更新后数据");		
			sqlStr="select * from users"; //SQL查询语句		
			rs=st.executeQuery(sqlStr); //执行查询
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
		new UpdateDemo();
	}
}