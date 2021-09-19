package com.tjgd.filter;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.tjgd.bean.Auth;
import com.tjgd.bean.Employee;
import com.tjgd.bean.Role;
import com.tjgd.cache.MyCacheManager;
import com.tjgd.dao.impl.RoleDAOImpl;

@WebFilter(value = { "/manager/*" })
public class SecureFilter implements Filter {
	private FilterConfig config = null;
	private ServletContext ctx = null;
	private static final String LOGIN = "/manager/login.jsp";
	private static Vector<String> baseSourceContainer = new Vector<String>();
	private static Vector<String> fileContainer = new Vector<String>();
	private static Vector<String> actionContainer = new Vector<String>();

	// ----初始化信息------------------------------------------
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
		initSecureContainer();// 把允许的文件类型加载到secureContainer中,初始化安全容器
		initCache();// 初始化缓存结构
	}

	// -----初始化放行列表,后缀和路径放在两个不同的容器里面-------
	private void initSecureContainer() {
		// 放的是基本资源的后缀
		baseSourceContainer.add(".css");
		baseSourceContainer.add(".js");
		baseSourceContainer.add(".jpg");
		baseSourceContainer.add(".png");
		fileContainer.add("login.jsp");
		actionContainer.add("login");
	}

	// ------初始化缓存结构，把角色信息和权限信息都加载到缓存中----
	private void initCache() {
		MyCacheManager cache = new MyCacheManager();
		// 初始化缓存
		cache.initialize();// 对缓存进行初始化
		// 从数据库中添加所有的角色信息保存到缓存中
		List<Role> list = new RoleDAOImpl().listRoles();
		if (list != null) {
			for (Role role : list) {
				// 找到所有角色的权限信息
				List<Auth> l = new RoleDAOImpl().listAssigedAuths(role.getId());
				// 把所有角色的权限信息也都回到角色对象里去
				role.setAuthList(l);
				// 把所有角色信息都加载到缓存中
				cache.put(role.getId(), role);
			}
		}
		// 对权限进行测试
		Role r = cache.get(1);
		if (r != null) {
			for (int i = 0; i < r.getAuthList().size(); i++) {
				System.out.println(r.getAuthList().get(i).getAuthName());
			}
		}

	}

	// -------销毁容器--------------------------------------------
	public void destroy() {
		fileContainer.clear();
		baseSourceContainer.clear();
		actionContainer.clear();
	}

	// -------在过滤器中实现权限控制-------------------------------
	public void doFilter(ServletRequest sReq, ServletResponse sResp, FilterChain filterChain)
			throws IOException, ServletException {
		// 转化为HTTP请求和响应
		HttpServletRequest req = (HttpServletRequest) sReq;
		HttpServletResponse resp = (HttpServletResponse) sResp;
		 // 获取请求路径
		String path = req.getRequestURI();
		String action = req.getParameter("action");
		// 如果回话中的用户为空,页面重新定向到登陆页面
		if (!isLogined(req)) { // 如果是没有登录的用户，则执行以下的代码
			// 请求登录页面login.jsp或提交登录请求
			if (validateSuffix(path) || validatePage(path) || validateAction(action)) {
				filterChain.doFilter(req, resp);
			} else {
				ctx = config.getServletContext();
				// 如果用sendRedirect还会进行过滤，所以不用
				ctx.getRequestDispatcher(LOGIN).forward(req, resp);
				return;// 到这里时候已经出错了，不用再往下传递请求了。
			}
		} else {// 如果用户已经登录，在判断他是否有访问某一资源的权限
			try {
				// 如果用户没有当前页的权限,页面重新定向到上一页面
				HttpSession session = req.getSession();
				Employee emp = (Employee) session.getAttribute("emp");// 是在Servlet中放入的Employee对象，必须从session中获取emp对象
				fileContainer.add("top.jsp");
				actionContainer.add("logout");
				// 如果是基本登录页面或基本的图片等资源，则登录后就不进行限制。
				if (validateSuffix(path) || validatePage(path) || validateAction(action)) {
					filterChain.doFilter(sReq, sResp);
				} else if (checkSafe(req, emp)) {// 如果不是基本的资源，则要进行权限检查
					filterChain.doFilter(sReq, sResp);
				} else {
					// 权限不够界面
					String message = "您没有该权限，请联系管理员！";
					message += "<a href='javascript:window.history.go(-1)'>返回</a>";
					req.setAttribute("msg", message);
					ctx = config.getServletContext();
					ctx.getRequestDispatcher(LOGIN).forward(req, resp);
					return;
				}
			} catch (Throwable e) {
				throw new RuntimeException("权限过滤时候出现错误", e);
			} // end try
		} // end else
	}

	// ------对请求页进行检查------------------------------
	private boolean validatePage(String path) {
		int pos = path.lastIndexOf("/");
		String resource = path.substring(pos + 1);
		// 登陆之前放行的基本元素,因为这些元素都是登录页面显示时所必须要的。
		if (fileContainer.contains(resource)) {
			return true;
		}
		return false;
	}

	// ------对请求资源后缀进行检查------------------------
	private boolean validateSuffix(String path) {
		String suffix = "";
		int pos2 = path.lastIndexOf(".");// 当请求的路径中不存在"."时值为-1
		if (pos2 != -1) {
			suffix = path.substring(pos2);
		}
		if (baseSourceContainer.contains(suffix)) {
			return true;
		}
		return false;
	}

	// ---------对请求资源参数进行检查-----------------------
	private boolean validateAction(String action) {
		if (actionContainer.contains(action)) {
			return true;
		}
		return false;
	}

	// ---------判断是否是已经登陆的用户----------------------
	private boolean isLogined(HttpServletRequest request) {
		boolean flag = false;
		HttpSession session = request.getSession();
		if (session.getAttribute("emp") != null) {
			// 保存的是一个Employee对象
			flag = true;
		}
		return flag;
	}

	// ----------判断某个员工是否有操作的权限------------------
	private boolean checkSafe(HttpServletRequest request, Employee emp) {
		String url = request.getRequestURI();
		String action = request.getParameter("action");
		int index = url.lastIndexOf("/");
		if (index > -1) {// 在进行权限检查时要求最后的一部分
			url = url.substring(index + 1);
		}
		try {
			System.out.println("资源请求路径URL+action=" + url+","+action);
			return emp.checkSafe(url, action);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
