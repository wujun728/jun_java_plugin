package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class InputServlet extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse resp)
              throws ServletException,IOException{
		String info = req.getParameter("info") ;	// 假设参数名称为info
		PrintWriter out = resp.getWriter() ;
		out.println("<html>") ;
		out.println("<head><title>MLDNJAVA</title></head>") ;
		out.println("<body>") ;
		out.println("<h1>" + info + "</h1>") ;
		out.println("</body>") ;
		out.println("</html>") ;
		out.close() ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp)
              throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}