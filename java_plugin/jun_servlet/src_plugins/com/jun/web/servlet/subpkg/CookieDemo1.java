package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ��һ��cookie�ĳ���
 * @author APPle
 *
 */
public class CookieDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.����Cookie����
		Cookie cookie1 = new Cookie("name","eric");
		//Cookie cookie2 = new Cookie("email","jacky@qq.com");
		//Cookie cookie1 = new Cookie("email","eric@qq.com");
		
		
		/**
		 * 1������cookie����Ч·����Ĭ���������Ч·���ڵ�ǰwebӦ���¡� /day11
		 */
		//cookie1.setPath("/day11");
		//cookie2.setPath("/day12");
		
		/**
		 * 2)����cookie����Чʱ��
		 * �������ʾcookie��ݱ���������Ļ���Ŀ¼��Ӳ���У�����ֵ��ʾ�����ʱ�䡣
			�������ʾcookie��ݱ�����������ڴ��С�������ر�cookie�Ͷ�ʧ�ˣ���
			�㣺��ʾɾ��ͬ���cookie���

		 */
		//cookie1.setMaxAge(20); //20�룬����󲻵���cookie��ʼ����
		cookie1.setMaxAge(-1); //cookie������������ڴ棨�Ựcookie��
		//cookie1.setMaxAge(0);
		
		
		//2.��cookie��ݷ��͵��������ͨ����Ӧͷ���ͣ� set-cookie��ƣ�
		//response.setHeader("set-cookie", cookie.getName()+"="+cookie.getValue()+",email=eric@qq.com");
		//�Ƽ�ʹ�����ַ����������ֶ�����cookie��Ϣ
		response.addCookie(cookie1);
		//response.addCookie(cookie2);
		//response.addCookie(cookie1);
		

		
		//3.������������͵�cookie��Ϣ
		/*String name = request.getHeader("cookie");
		System.out.println(name);*/
		Cookie[] cookies = request.getCookies();
		//ע�⣺�ж�null,�����ָ��
		if(cookies!=null){
			//����
			for(Cookie c:cookies){
				String name = c.getName();
				String value = c.getValue();
				System.out.println(name+"="+value);
			}
		}else{
			System.out.println("û�н���cookie���");
		}
		
	}

}
