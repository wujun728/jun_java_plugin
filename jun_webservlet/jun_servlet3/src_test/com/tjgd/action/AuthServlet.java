package com.tjgd.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tjgd.bean.Auth;
import com.tjgd.bean.Module;
import com.tjgd.dao.IAuthDAO;
import com.tjgd.dao.IModuleDAO;
import com.tjgd.dao.impl.AuthDAOImpl;
import com.tjgd.dao.impl.ModuleDAOImpl;
import javax.servlet.annotation.WebServlet;
@WebServlet("/manager/authServlet/*")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IAuthDAO aservice = null;
	IModuleDAO mservice = null;
    private static final String ADDAUTH="/manager/auth/addAuth.jsp";
    private static final String LIST="/manager/auth/list.jsp";
    //--------doGet()鏂规硶------------------------
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
    //--------doPost()鏂规硶------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equals("list")) {
			listAuths(req, resp);
		} else if (action.equals("delete")) {
			deleteAuth(req, resp);
		} else if (action.equals("add")) {
			add(req, resp);
		} else if (action.equals("save")) {
			save(req, resp);
		}
	}
	//--------杞埌娣诲姞鏉冮檺鐣岄潰,闇�鍔犺浇鎵�湁妯″潡------
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		mservice = new ModuleDAOImpl();
		// 鍔犺浇鎵�湁妯″潡
		List<Module> list = mservice.listModules();
		req.setAttribute("moduleList", list);
		req.getRequestDispatcher(ADDAUTH).forward(req, resp);
	}
    //--------瀛樺偍鏉冮檺淇℃伅--------------------------
	public void save(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		aservice = new AuthDAOImpl();
		Auth auth = new Auth();
		auth.setAuthName(req.getParameter("authName"));
		auth.setActionName(req.getParameter("actionName"));
		int moduleId = Integer.parseInt(req.getParameter("moduleId"));
		Module m = new Module();
		m.setId(moduleId);
		auth.setUrl(createModuleURL(m));
		auth.setModuleId(moduleId);
		aservice.saveAuth(auth);
		listAuths(req, resp);
	}
	//---------鏋勯�妯″潡URL----------------------------
	private String createModuleURL(Module module) {
		if (module.getId() == 1) {
			return "employeeServlet";
		} else if (module.getId() == 2) {
			return "roleServlet";
		} else if (module.getId() == 3) {
			return "authServlet";
		} else {
			return "authServlet";
		}
	}
	//----------寰楀埌鏉冮檺鍒楄〃-------------------------------
	public void listAuths(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		aservice = new AuthDAOImpl();
		List<Auth> list = aservice.listAuths();
		req.setAttribute("listAuths", list);
		req.getRequestDispatcher(LIST).forward(req, resp);
	}
	//----------鍒犻櫎鐗瑰畾鐨勬潈闄�-------------------------
	public void deleteAuth(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		aservice = new AuthDAOImpl();
		int id = Integer.parseInt(req.getParameter("authId"));
		aservice.delAuth(id);
		listAuths(req, resp);// 杞埌鏉冮檺鍒楄〃
	}
}
