package com.jun.plugin.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * ��ȡwebӦ���µ���Դ�ļ�������properties��
 * @author APPle
 */
public class ResourceDemo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 *  . ���java��������Ŀ¼��java��������������� ��tomcat/binĿ¼��
		 *   ���ۣ� ��web��Ŀ�У� . �����tomcat/binĿ¼�¿�ʼ�����Բ���ʹ���������·����
		 */
		
		
		//��ȡ�ļ�����web��Ŀ�²�Ҫ�����ȡ����Ϊ.��ʾ��tomcat/binĿ¼��
		/*File file = new File("./src/db.properties");
		FileInputStream in = new FileInputStream(file);*/
		
		/**
		 * ʹ��webӦ���¼�����Դ�ļ��ķ���
		 */
		/**
		 * 1. getRealPath��ȡ,������Դ�ļ��ľ��·��
		 */
		/*String path = this.getServletContext().getRealPath("/WEB-INF/classes/db.properties");
		System.out.println(path);
		File file = new File(path);
		FileInputStream in = new FileInputStream(file);*/
		
		/**
		 * 2. getResourceAsStream() �õ���Դ�ļ������ص���������
		 */
		InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
		
		
		Properties prop = new Properties();
		//��ȡ��Դ�ļ�
		prop.load(in);
		
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		System.out.println("user="+user);
		System.out.println("password="+password);
		
	}

}
