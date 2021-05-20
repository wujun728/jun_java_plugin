package com.tjgd.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tjgd.bean.Auth;
import com.tjgd.bean.Employee;
import com.tjgd.bean.Property;
import com.tjgd.bean.Role;
import com.tjgd.cache.MyCacheManager;
import com.tjgd.dao.IAuthDAO;
import com.tjgd.dao.IEmployeeDAO;
import com.tjgd.dao.IModuleDAO;
import com.tjgd.dao.impl.EmployeeDAOImpl;
@WebServlet("/manager/employeeServlet/*")
public class EmployeeServlet extends HttpServlet {
	IEmployeeDAO eservice = null;
	IAuthDAO aservice = null;
	IModuleDAO mservice = null;
	private static String SUCCESS="/manager/commons/top.jsp";
	private static String LOGIN="/manager/login.jsp";
	private static String ADDEMP="/manager/employee/addEmp.jsp";
	private static String LIST="/manager/employee/list.jsp";
	private static String ASSIGNROLE="/manager/employee/assignRole.jsp";
    //---------doGet()方法--------------------------------------
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	//---------doGet()方法--------------------------------------
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equals("login")) {
			login(req, resp);
		} else if (action.equals("logout")) {
			logout(req, resp);
		} else if (action.equals("list")) {
			list(req, resp);
		} else if (action.equals("delete")) {
			delete(req, resp);
		} else if (action.equals("add")) {
			add(req, resp);
		} else if (action.equals("save")) {
			save(req, resp);
		} else if (action.equals("assign")) {
			assign(req, resp);
		} else if (action.equals("assignRole")) {
			assignRole(req, resp);
		}
	}
	//----------进行登录的操作--------------------------------------
	public void login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		    Employee emp=null;
			eservice=new EmployeeDAOImpl();
			Employee empb = getEmployeeFromReq(req);
			List<Employee> list=eservice.listEmployees();;
			for (int i = 0; i < list.size(); i++) {
			    Employee temp=(Employee)list.get(i);
			    if(temp.getEmpName().equals(empb.getEmpName())&& temp.getPassword().equals(empb.getPassword())){
			    	emp=temp;
			    }
		    }
		if (emp!= null) {// 如果不为空，说明登录成功
			List<Role> roles = new EmployeeDAOImpl().findAllAssignedRoles(emp.getId());
			MyCacheManager cache = new MyCacheManager();
			cache.initialize();// 初始化
			System.out.println("test:"+roles);
			if (roles != null && roles.size() != 0) {
				for (Role role : roles) {
					role = cache.get(role.getId());// 获取特定角色的所有权限
					List<Auth> auths = role.getAuthList();
					// 使得员工得到所有权限，并且不重复
					if (auths != null && auths.size() != 0) {
						authFilter(emp, auths);
					}
				}
			}
			HttpSession session = req.getSession();
			session.setAttribute("emp", emp);//将Employee对象保存到session范围内
			req.getRequestDispatcher(SUCCESS).forward(req, resp);
		} else {
			req.setAttribute("msg", "用户名或密码错误");
			req.getRequestDispatcher(LOGIN).forward(req, resp);
		}
	}
	// ------------利用request对象生成 Employee--------------------------------
	private Employee getEmployeeFromReq(HttpServletRequest request) {
		Employee emp = new Employee();
		String id = request.getParameter("empId");
		if (id != null && !id.equals("")) {// 如果是登录的时候，则没有id号
			emp.setId(Integer.parseInt(id));
		}
		String position = request.getParameter("position");
		if (position != null && !position.equals("")) {// 如果是登录的时候，则没有职位值
			emp.setPosition(position);
		}

		emp.setEmpName(request.getParameter("empname"));
		emp.setPassword(request.getParameter("psd"));
		return emp;
	}
	//---------注销模块，退出后转到login.jsp--------------------------------
	public void logout(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		req.getRequestDispatcher(LOGIN).forward(req, resp);
	}
    //---------打开添加员工信息页面
	public void add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher(ADDEMP).forward(req,resp);
	}
	//----------增加员工----------------------------------------------------
	public void save(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		eservice=new EmployeeDAOImpl();
		Employee emp = new Employee();
		emp.setPosition(req.getParameter("position"));
		emp.setEmpName(req.getParameter("empName"));
		String psd = req.getParameter("psd");
		if (psd == null || psd.equals("")) {
			psd = "666666";// 员工如果不指定密码，则设置初始密码
		}
		emp.setPassword(psd);
		eservice.saveEmployee(emp);
		list(req, resp);
	}
	//-----------从数据库中查询所有的员工--------------------------------------
	public void list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		eservice=new EmployeeDAOImpl();
		List<Employee> list =eservice.listEmployees();
		req.setAttribute("list", list);
		req.getRequestDispatcher(LIST).forward(req,resp);
	}
	//------------删除员工信息,当删除员工信息时，需要对emp_role表也进行删除----
	public void delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		eservice=new EmployeeDAOImpl();
		int id = Integer.parseInt(req.getParameter("empId"));
		eservice.delEmployee(id);
		list(req, resp);
	}
	//------------转到分配角色界面---------------------------------------------
	public void assignRole(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("empId"));
		Employee emp = new EmployeeDAOImpl().findByID(id);
		req.setAttribute("emp", emp);
		// 未分配的角色
		List<Role> notAssignedRole = new EmployeeDAOImpl()
				.findNotAssignedRoles(id);
		// 已分配的角色
		List<Role> assignedRole = new EmployeeDAOImpl().findAllAssignedRoles(id);
		req.setAttribute("assignedRole", assignedRole);
		req.setAttribute("notAssignedRole", notAssignedRole);
		req.getRequestDispatcher(ASSIGNROLE).forward(req, resp);
	}
	//-------------为员工分配角色------------------------------------------------
	public void assign(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		eservice=new EmployeeDAOImpl();
		Employee emp = getEmpAndRoleFromReq(req);
		eservice.assignRoleForEmp(emp);
		list(req, resp);

	}

	//--------------获取 员工的所有角色--------------------------------------------
	private Employee getEmpAndRoleFromReq(HttpServletRequest req) {
		Employee emp = new Employee();
		String empid = req.getParameter("empId");
		emp.setId(Integer.parseInt(empid));
		// 已分配角色
		String[] assignedRoles = req.getParameterValues("ado");
		System.out.println("length======="+assignedRoles);
		if (assignedRoles != null) {
			for (String id : assignedRoles) {
				Role role = new Role();
				role.setId(Integer.parseInt(id));
				emp.getRoles().add(role);
			}
		}
		return emp;// 此emp对象中有员工的id和所有角色的id值
	}
	//--------------过滤重复权限，构造一个权限唯一的权限列表------------------------
	private void authFilter(Employee emp, List<Auth> auths) {
		for (Auth auth : auths) {
			Property pro = new Property(auth.getUrl(), auth.getActionName());
			emp.addProperty(pro);
		}
	}
}
