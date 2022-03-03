package com.jun.plugin.blog.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.jun.plugin.blog.entity.Blog;
import com.jun.plugin.blog.entity.BlogType;
import com.jun.plugin.blog.entity.Blogger;
import com.jun.plugin.blog.entity.Link;
import com.jun.plugin.blog.service.BlogService;
import com.jun.plugin.blog.service.BlogTypeService;
import com.jun.plugin.blog.service.BloggerService;
import com.jun.plugin.blog.service.LinkService;

/**
 * ��ʼ����� �Ѳ�����Ϣ ���ݲ�����������Ϣ �������ڹ鵵������Ϣ ��ŵ�application�У������ṩҳ����������
 * @author Wujun
 *
 */
@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext=applicationContext;
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application=servletContextEvent.getServletContext();
		BloggerService bloggerService=(BloggerService) applicationContext.getBean("bloggerService");
		Blogger blogger=bloggerService.find(); // ��ѯ������Ϣ
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		BlogTypeService blogTypeService=(BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList=blogTypeService.countList(); // ��ѯ��������Լ����͵�����
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		BlogService blogService=(BlogService) applicationContext.getBean("blogService");
		List<Blog> blogCountList=blogService.countList(); // �������ڷ����ѯ����
		application.setAttribute("blogCountList", blogCountList);
		
		LinkService linkService=(LinkService) applicationContext.getBean("linkService");
		List<Link> linkList=linkService.list(null); // ��ѯ���е�����������Ϣ
		application.setAttribute("linkList", linkList);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
