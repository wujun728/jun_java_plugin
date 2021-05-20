package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;

public class HelloServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp)
              throws ServletException,IOException{
		PrintWriter out = resp.getWriter() ;
		out.println("<html>") ;
		out.println("<head><title>MLDNJAVA</title></head>") ;
		out.println("<body>") ;
		out.println("<h1>HELLO WORLD</h1>") ;
		out.println("</body>") ;
		out.println("</html>") ;
		out.close() ;
	}
}