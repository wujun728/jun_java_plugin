package com.jun.plugin.servlet.guice.user.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.jun.plugin.servlet.guice.user.UserModule;
import com.jun.plugin.servlet.guice.user.entity.User;
import com.jun.plugin.servlet.guice.user.service.UserService;
import com.jun.plugin.servlet.guice.user.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@Singleton
public class UserServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	
	@Inject
	private Injector inj;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String account = request.getParameter("account");
		int userId = Integer.valueOf(request.getParameter("userid"));
		User u = new User();
		u.setAccount(account);
		u.setUser_id(userId);
		List<User> ulist=new ArrayList<>();
		ulist.add(u);
		//UserService userService = new UserServiceImpl();
		try {
			//inj.getInstance(UserService.class).add(u);
			userService.add(u);
			
			System.out.println("ok");
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
			// 注意：调用service层的方法出异常之后，继续将异常抛出，这样在TransactionFilter就能捕获到抛出的异常，继而执行事务回滚操作
			throw new RuntimeException(e);
		}

	}

	 public static void main(String[] args) throws Exception {  
	        Injector injector = Guice.createInjector(new UserModule());  
	        UserService service = injector.getInstance(UserServiceImpl.class);  
	        User u = new User();
			u.setAccount("google guice!");
			u.setUser_id(1201);
			service.add(u);
	    }  
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
