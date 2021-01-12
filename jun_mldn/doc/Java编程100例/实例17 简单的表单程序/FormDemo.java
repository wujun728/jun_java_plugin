import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FormDemo extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response){		
		try{
			response.setContentType("text/html;charset=gb2312"); //设置头部
			PrintWriter out=response.getWriter();  //得到PrintWriter实例
			String name,age,sex,phone,address,email;  //变量声明
			name=request.getParameter("Name");  //得到参数
			sex=request.getParameter("Sex");
			phone=request.getParameter("Phone");
			address=request.getParameter("Address");
			email=request.getParameter("Email");
			out.println("<HTML><HEAD><TITLE>a</TITLE></HEAD>");  //输出信息到客户端
			out.println("<BODY>");
			out.println("<P><H3>名字："+convertToChinese(name)+"</H3></P>");
			out.println("<P><H3>性别："+convertToChinese(sex)+"</H3></P>");
			out.println("<P><H3>电话："+phone+"</H3></P>");
			out.println("<P><H3>地址："+convertToChinese(address)+"</H3></P>");
			out.println("<P><H3>电子邮件："+email+"</H3></P>");
			out.println("</BODY></HTML>");			
		
		}
		catch (Exception ex){
			ex.printStackTrace();  //输出错误信息
		}
	}
	
	private String convertToChinese(String source){
		String s="";
		try{
			 s=new String(source.getBytes("ISO8859_1"));  //字符编码转换
		}
		catch(java.io.UnsupportedEncodingException ex){
			ex.printStackTrace();  //输出错误信息
		}
		return s; //返回转换后的字符串
	}	
}