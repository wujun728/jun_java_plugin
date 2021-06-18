import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;

public class ServletDemo extends HttpServlet{	

	private String dbURL="jdbc:odbc:example";	// 数据库标识名
	private String user="devon";	// 数据库用户
	private String password="book";	// 数据库用户密码
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

	public void doGet(HttpServletRequest req,HttpServletResponse res)  {
		try{
	    	res.setContentType("text/html"); //设置类型      
	        
	        //连接并查询数据库

	        String sqlStr="select * from users"; //SQL查询语句
			Statement st=con.createStatement(); //获取PreparedStatement对象
			ResultSet rs=st.executeQuery(sqlStr); //执行查询
			
			//在网页中输出查询结果		
			PrintWriter out = new PrintWriter(res.getOutputStream()); //获取输出流
	    	out.println("<html>"); //输出数据
	        out.println("<head>");
	        out.println("<title>SimpleDB</title>");
	        out.println("</head>");	
	        out.println("<body>");
			out.println("<center><table border='2'>");	    	
	    	ResultSetMetaData rsmd = rs.getMetaData(); //获取ResultSetMetaData对象
	    	int colCount = rsmd.getColumnCount(); //获取列数量
	    	out.println("<tr>");
	    	for(int i=0; i<colCount; i++){
	    		out.println("<th>"+rsmd.getColumnLabel(i+1)+"</th>"); //获取列标题
	    	}
	    	out.println("</tr>");
	    	while(rs.next()){ //遍历结果
	    	 	out.println("<tr>");
	    	 	for(int i=0; i<colCount; i++) {
	    	 	 	out.println("<td>"+rs.getString(i+1)+"</td>"); //输出数据
	    	 	}
	    	 	out.println("</tr>");
	    	}
	    	out.println("</table></center>");	    	
	     	out.println("</body></html>");	     

	     	out.close();  //关闭输出流
	     	
	   	}
	   	catch (Exception ex){
			ex.printStackTrace();  //输出出错信息
	   	}
	}
	
	public void destroy() {
		try {
			con.close(); //关闭连接
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}