package com.itheima.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.omg.CORBA.UserException;

import com.itheima.domain.User;
import com.itheima.exception.UserHasExistException;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
import com.itheima.util.BeanUtil;
import com.itheima.web.bean.UserFormBean;
//练习重点
public class RegistServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserFormBean formBean = null;
		try {
			//封装数据
			formBean = BeanUtil.fillBean(request, UserFormBean.class);
			//验证数据的合法性:回显
			if(!formBean.validate()){
				request.setAttribute("formBean", formBean);
				request.getRequestDispatcher("/regist.jsp").forward(request, response);
				return;
			}
			//填充模型：回显(把FormBean中的数据，搞到JavaBean中。注意类型的转换)
			
			User user = new User();
//		user.setUsername(formBean.getUsername());
//		user.setPassword(formBean.getPassword());
//		user.setEmail(formBean.getEmail());
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		user.setBirthday(df.parse(formBean.getBirthday()));
			
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, formBean);//别搞反了
			
			
			//保存数据：调用业务层，给出成功提示
			s.regist(user);
			response.sendRedirect(request.getContextPath());//成功：重定向到主页
		}catch(UserHasExistException e){
			//用户名存在的问题：回显  抓异常的形式捕获
			formBean.getErrors().put("username", e.getMessage());
			request.setAttribute("formBean", formBean);
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
