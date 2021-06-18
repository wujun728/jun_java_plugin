import java.sql.*;

public class TableDemo{
	private String dbURL="jdbc:odbc:example";	//数据库标识名
	private String user="devon";	//数据库用户
	private String password="book";	//数据库用户密码
	
	public TableDemo(){
		try	{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	//装载数据库驱动
			Connection con=DriverManager.getConnection(dbURL,user,password); //得到连接
			Statement st=con.createStatement();
			
			//新建表
			System.out.println("新建表：products");  //输出信息到控制台
			String sqlStr="create table products(Name varchar(20),Price float,Provider varchar(40),Count int)"; //新建表的SQL语句
			st.executeUpdate(sqlStr); //执行SQL语句,新建表
			
			//插入数据
			sqlStr="insert into products(Name,Price,Provider,Count) values(\'面包\',2.5,\'上海\',20)"; //插入数据SQL语句
			st.executeUpdate(sqlStr); //执行插入
			sqlStr="insert into products(Name,Price,Provider,Count) values(\'蛋糕\',5.5,\'北京\',13)";
			st.executeUpdate(sqlStr);			
			
			//显示数据
			sqlStr="select * from products"; //查询数据SQL语句
			ResultSet rs=st.executeQuery(sqlStr); //获取结果集
			String name,provider; 
			float price;
			int count;
			while (rs.next()){
				name=rs.getString("Name"); //取得查询结果
				price=rs.getFloat("Price");
				provider=rs.getString("Provider");
				count=rs.getInt("Count");
				System.out.println("Name:"+name+"\tPrice:"+price+"\tProvider:"+provider+"\tCount:"+count); //显示查询结果
			}
			
			//删除表
			System.out.println("删除表：products"); 
			sqlStr="drop table products"; //删除表SQL语句
			st.executeUpdate(sqlStr); //执行删除			

			con.close(); //关闭连接			
		}
		catch (Exception ex)	{
			ex.printStackTrace();  //输出出错信息
		}
	}
	
	public static void main(String args[]){
		new TableDemo();
	}
}