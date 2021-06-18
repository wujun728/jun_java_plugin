import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class DisplayDemo extends JFrame{
	private String dbURL="jdbc:microsoft:sqlserver://202.115.26.181:1433";	// 数据库标识名
	private String user="devon";	// 数据库用户
	private String password="book";		// 数据库用户密码
	private JTable table;
	
	public DisplayDemo(){
		super("显示数据库查询结果"); //调用父类构造函数
		String[] columnNames={"用户名","年龄","性别","Email"}; //列名
		Object[][] rowData=new Object[5][4]; //表格数据
		
		try	{			
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");  //加载驱动器
			Connection con=DriverManager.getConnection(dbURL,user,password); //获取连接
			String sqlStr="select * from users"; //查询语句
			PreparedStatement ps=con.prepareStatement(sqlStr); //获取PreparedStatement对象
			ResultSet rs=ps.executeQuery(); //执行查询
			String name,sex,email; //查询结果
			int age;
			int count=0;
			while (rs.next()){	//遍历查询结果			
				rowData[count][0]=rs.getString("name"); //初始化数组内容
				rowData[count][1]=Integer.toString(rs.getInt("age"));
				rowData[count][2]=rs.getString("sex");
				rowData[count][3]=rs.getString("email");
				count++;
			}				

			con.close();  //关闭连接
		}
		catch(Exception ex){
			ex.printStackTrace();  //输出出错信息
		}
		
		Container container=getContentPane();  //获取窗口容器
		table=new JTable(rowData,columnNames); //实例化表格
		table.getColumn("年龄").setMaxWidth(25);  //设置行宽
		container.add(new JScrollPane(table),BorderLayout.CENTER); //增加组件
		
		
		setSize(300,200);  //设置窗口尺寸
		setVisible(true);  //设置窗口可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //关闭窗口时退出程序
	}
	
	public static void main(String[] args){
		new DisplayDemo();
	}
}