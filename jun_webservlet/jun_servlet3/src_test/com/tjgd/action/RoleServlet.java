package com.tjgd.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tjgd.bean.Auth;
import com.tjgd.bean.Role;
import com.tjgd.dao.IRoleDAO;
import com.tjgd.dao.impl.RoleDAOImpl;
@WebServlet("/manager/roleServlet/*")
public class RoleServlet extends HttpServlet {
	IRoleDAO rservice=null;
	private static String LIST="/manager/role/list.jsp";
	private static String ADDROLE="/manager/role/addRole.jsp";
	private static String ASSIGNAUTH="/manager/role/assignAuth.jsp";
	//--------doPost()方法---------------------------------
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
    //--------doPost()方法---------------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equals("list")) {
			list(req, resp);
		}else if (action.equals("delete")) {
			delete(req, resp);
		} else if (action.equals("add")) {
			add(req, resp);
		} else if (action.equals("save")) {
			save(req, resp);
		}else if (action.equals("assignAuth")) {
			assignAuth(req, resp);
		} else if (action.equals("assign")) {
			assign(req, resp);
		} 
	}
	// ----------返回角色列表--------------------------------
	public void list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rservice = new RoleDAOImpl();
		List<Role> list = rservice.listRoles();
		req.setAttribute("list", list);
		req.getRequestDispatcher(LIST).forward(req, resp);
	}
	// ----------删除角色-----------------------------------
	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rservice = new RoleDAOImpl();
		int id = Integer.parseInt(req.getParameter("roleId"));
		rservice.delRole(id);
		list(req, resp);
	}
	//---------转到添加角色页面------------------------------
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher(ADDROLE).forward(req, resp);
	}
	//---------保存角色--------------------------------------
	public void save(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rservice = new RoleDAOImpl();
		Role role = getRoleFromReq(req);
		rservice.saveRole(role); // 回到列表
		list(req, resp);
	}
	//----------获取角色对象----------------------------------
	private Role getRoleFromReq(HttpServletRequest req) {

		Role role = new Role();
		String id = req.getParameter("roleId");
		if (id != null && !id.equals("")) {
			role.setId(Integer.parseInt(id));
		}
		String name = req.getParameter("roleName");
		role.setRoleName(name);
		return role;
	}
	// -------跳转到权限分配界面------------------------------
	public void assignAuth(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("roleId"));
		Role role = new RoleDAOImpl().findRoleByID(id);
		req.setAttribute("role", role);
		// 查找已经分配的和没有分配的权限
		List<Auth> notAssigned = new RoleDAOImpl().notAssignedAuths(id);
		req.setAttribute("notAssignedAuth", notAssigned);
		List<Auth> assignedAuth = new RoleDAOImpl().listAssigedAuths(id);
		req.setAttribute("assignedAuth", assignedAuth);
		req.getRequestDispatcher(ASSIGNAUTH).forward(req,resp);

	}
	// -------为员工分配权限----------------------------------
	public void assign(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		rservice = new RoleDAOImpl();
		Role role = getRoleAndAuthFromReq(req);
		rservice.assignAuthForRole(role);
		list(req, resp);
	}
	// --------构造权限和角色---------------------------------
	private Role getRoleAndAuthFromReq(HttpServletRequest req) {
		Role role = new Role();
		String roleid = req.getParameter("roleId");
		role.setId(Integer.parseInt(roleid));
		// 已分配权限
		String[] assignedAuths = req.getParameterValues("ado");
		if (assignedAuths != null) {
			for (String id : assignedAuths) {
				Auth auth = new Auth();
				auth.setId(Integer.parseInt(id));
				role.getAuthList().add(auth);
			}
		}
		return role;
	}
}
