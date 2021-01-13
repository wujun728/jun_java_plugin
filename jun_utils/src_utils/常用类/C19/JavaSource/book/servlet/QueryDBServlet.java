package book.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.CachedRowSet;

import book.database.DBConnector;
import book.servlet.page.PageableResultSet;
import book.servlet.page.PageableResultSetImpl;

import com.sun.rowset.CachedRowSetImpl;
/**
 * 查询数据库的Servlet
 */
public class QueryDBServlet extends HttpServlet {
	
	// 数据库连接变量
	Connection con = null;
	Statement sm = null;
	/**
	 * 初始化Servlet实例时调用该方法
	 */
	public void init() throws ServletException {
		super.init();
		// 从web.xml中读取servlet的配置参数
		String dbURL = this.getServletConfig().getInitParameter("dbURL");
		String driver = this.getServletConfig().getInitParameter("driver");
		String username = this.getServletConfig().getInitParameter("username");
		String password = this.getServletConfig().getInitParameter("password");
		try {
			// 连接数据库
			con = DBConnector.getConnection(driver, dbURL, username, password);
			sm = con.createStatement();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	/**
	 * 处理doGet请求，当JSP表单中的method为get时，会调用该方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	/**
	 * 处理doPost请求，当JSP表单中的method为post时，会调用该方法
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 从request中获取JSP页面上表单的输入参数
		String sql = request.getParameter("querySQL");
		int pageIndex = Integer.parseInt((String)request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt((String)request.getParameter("pageSize"));
		String servleturl = request.getParameter("servleturl");
		String errorurl = request.getParameter("errorurl");
		try {
			// 判断SQL语句是否为查询语句，以放置修改数据库
			sql = sql.trim();
			if (!sql.toUpperCase().startsWith("SELECT ")){
				throw new Exception("输入的SQL语句必须是查询语句！");
			}
			// 执行查询
			ResultSet rs = sm.executeQuery(sql);
			
			// 为了防止数据库连接被关闭时，ResultSet中的数据无法读取的问题
			// 在这里将ResultSet转换成CachedRowSet，它存在于内存中，不依赖于数据库。
			CachedRowSet crs = new CachedRowSetImpl();
			// populate方法把ResultSet中的数据导入到CachedRowSet中
			crs.populate(rs);
			
			// 构造能够分页的PageableResultSet对象
			PageableResultSet pageResultSet = new PageableResultSetImpl(crs);
			// 设置每页的记录数和当前页码
			pageResultSet.setPageSize(pageSize);
			pageResultSet.gotoPage(pageIndex);
			// 将结果放入session中
			HttpSession session = request.getSession();
			session.setAttribute("pageResultSet", pageResultSet);
			
			// 本Servlet处理完毕，将请求转发给下一页面。
			request.getRequestDispatcher(servleturl).forward(request, response);
		} catch (Exception e){
			// 查询错误时，清除Session的信息
			HttpSession session = request.getSession();
			session.removeAttribute("pageResultSet");
			// 出现异常时，记录异常信息
			request.setAttribute("errormsg", e.getMessage());
			// 将请求转发给错误页面
			request.getRequestDispatcher(errorurl).forward(request, response);
		}
	}
	/**
	 * 销毁Servlet实例时，调用该方法
	 */
	public void destroy() {
		super.destroy();
		try {
			// 关闭Statement
			if (sm != null){
				sm.close();
			}
		} catch (Exception e1){
		}
		try {
			// 关闭Connection
			if (con != null){
				con.close();
			}
		} catch (Exception e1){
		}
	}
	
	public String getServletInfo() {
		return "Query Database Servlet";
	}
}
