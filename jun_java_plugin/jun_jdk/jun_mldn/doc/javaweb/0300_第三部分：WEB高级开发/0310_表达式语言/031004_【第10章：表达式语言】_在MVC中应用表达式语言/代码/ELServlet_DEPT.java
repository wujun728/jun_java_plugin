package org.lxh.eldemo.servlet ;
import org.lxh.eldemo.vo.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class ELServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException,ServletException{
		Dept dept = new Dept() ;
		dept.setDeptno(10) ;
		dept.setDname("MLDN教学部") ;
		dept.setLoc("北京西城区") ;
		request.setAttribute("deptinfo",dept) ;
		request.getRequestDispatcher("dept_info.jsp").forward(request,response) ;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException,ServletException{
		this.doGet(request,response) ;
	}
}