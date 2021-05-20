package org.lxh.eldemo.servlet ;
import java.util.* ;
import org.lxh.eldemo.vo.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class ELServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException,ServletException{
		List<Dept> all = new ArrayList<Dept>() ;
		Dept dept = null ;
		dept = new Dept() ;
		dept.setDeptno(10) ;
		dept.setDname("MLDN教学部") ;
		dept.setLoc("北京市西城区") ;
		all.add(dept) ;
		dept = new Dept() ;
		dept.setDeptno(20) ;
		dept.setDname("MLDN研发部") ;
		dept.setLoc("北京市东城区") ;
		all.add(dept) ;
		request.setAttribute("alldept",all) ;
		request.getRequestDispatcher("dept_list.jsp").forward(request,response) ;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws java.io.IOException,ServletException{
		this.doGet(request,response) ;
	}
}