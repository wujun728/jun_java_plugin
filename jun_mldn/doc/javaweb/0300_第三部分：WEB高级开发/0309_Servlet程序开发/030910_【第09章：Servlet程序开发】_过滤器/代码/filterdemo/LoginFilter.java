package org.lxh.filterdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class LoginFilter implements Filter {
	public void init(FilterConfig config)
          throws ServletException{
	}
	public void doFilter(ServletRequest request,
              ServletResponse response,
              FilterChain chain)
              throws IOException,
                     ServletException{
		// session属于http协议的范畴
		HttpServletRequest req = (HttpServletRequest) request ;
		HttpSession ses = req.getSession() ;
		if(ses.getAttribute("userid") != null) {
			// 已经登陆过了，则可以访问
			chain.doFilter(request,response) ;
		} else {
			request.getRequestDispatcher("login.jsp").forward(request,response) ;
		}
	}
	public void destroy(){
	}
}