package cn.itlaobing.web.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itlaobing.web.controller.HelloController;

@WebServlet("/hello")
public class DispatcherServlet extends HttpServlet {

	Log log = LogFactory.getLog(DispatcherServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 查看ServletContext范围中存放所有的key-value

		ServletContext ctx = this.getServletContext();// 获取ServletContext对象

		// 获取Spring容器
		WebApplicationContext wctx = WebApplicationContextUtils.getWebApplicationContext(ctx);

		// 获取请求地址
		String requestUrl = req.getServletPath();
		// 因为请求地址就是 Controller对象在spring容器中的标识，所以我们可以通过 请求的地址获取Controller对象

		HelloController helloController = wctx.getBean(requestUrl, HelloController.class);
		helloController.doGet(req, resp);

		/*Enumeration<String> e = ctx.getAttributeNames();// 获取范围中所有的key
		while (e.hasMoreElements()) {
			String key = e.nextElement(); // 范围中的key
			Object value = ctx.getAttribute(key); // key所对应的value
			log.info(key + ">>>>>>>>>>>>>>>>>>>>>>>>" + value.getClass().getName());

			if (value instanceof WebApplicationContext) {
				log.info("找到了springContext！！！！！！！");
			}
		}*/

	}

}
