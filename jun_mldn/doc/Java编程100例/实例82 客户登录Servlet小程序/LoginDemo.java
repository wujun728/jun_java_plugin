import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginDemo extends HttpServlet{
	private String dbURL="jdbc:odbc:example";	//数据库标识名
	private String user="devon";	//数据库用户
	private String password="book";	//数据库用户密码
	private Connection con;
	
	
	public void init() {
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  //装载数据库驱动
	        con=DriverManager.getConnection(dbURL,user,password); //获取连接
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public void doGet(HttpServletRequest request,HttpServletResponse response){		
		try{
			response.setContentType("text/html;charset=gb2312"); //设置头部
			PrintWriter out=response.getWriter();  //得到PrintWriter实例
			String name,password;  //变量声明
			name=request.getParameter("name");  //得到参数
			password=request.getParameter("password"); 

			out.println("<HTML><HEAD><TITLE>a</TITLE></HEAD>");  //输出信息到客户端
			out.println("<BODY>");
			
			if (validateUser(name,password)){  //调用验证用户方法
				out.println("<P><H3>验证成功</H3></P>"); //显示验证结果
			}
			else{
				out.println("<P><H3>验证失败</H3></P>");
			}

			out.println("</BODY></HTML>");			
		
		}
		catch (Exception ex){
			ex.printStackTrace();  //输出错误信息
		}
	}
	

	public boolean validateUser(String username, String password) {
		try{
		    String sqlStr="select * from users where name=\'"+username+"\' and password=\'"+password+"\'"; //SQL查询语句
			Statement st=con.createStatement(); //获取PreparedStatement对象
			ResultSet rs=st.executeQuery(sqlStr); //执行查询		
			if (rs.next()){ //查询结果
				return true;
			}
			st.close(); 
		}
		catch (Exception ex){
			ex.printStackTrace();			
			return false;
		}
		return false;
	}
	
	public void destroy() {
		try {
			con.close(); //关闭连接
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}