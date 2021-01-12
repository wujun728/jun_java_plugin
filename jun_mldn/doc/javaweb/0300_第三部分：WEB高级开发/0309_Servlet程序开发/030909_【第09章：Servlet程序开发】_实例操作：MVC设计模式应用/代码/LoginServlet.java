package org.lxh.mvcdemo.servlet ;
import java.io.* ;
import java.util.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
import org.lxh.mvcdemo.factory.* ;
import org.lxh.mvcdemo.vo.* ;
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		String path = "login.jsp" ;
		String userid = req.getParameter("userid") ;
		String userpass = req.getParameter("userpass") ;
		List<String> info = new ArrayList<String>() ;	// 收集错误
		if(userid==null || "".equals(userid)){
			info.add("用户id不能为空！") ;
		}
		if(userpass==null || "".equals(userpass)){
			info.add("密码不能为空！") ;
		}
		if(info.size()==0){	// 里面没有记录任何的错误
			User user = new User() ;
			user.setUserid(userid) ;
			user.setPassword(userpass) ;
			try{
				if(DAOFactory.getIUserDAOInstance().findLogin(user)){
					info.add("用户登陆成功，欢迎" + user.getName() + "光临！") ;
				} else {
					info.add("用户登陆失败，错误的用户名和密码！") ;
				}
			}catch(Exception e){
				e.printStackTrace() ;
			}
		}
		req.setAttribute("info",info) ;
		req.getRequestDispatcher(path).forward(req,resp) ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}


}