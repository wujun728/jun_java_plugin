package com.jun.web.biz.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.web.biz.domain.User;
/*
 *   Ȩ�޿��ƵĹ����� 
 *   
 *   	1. ��ȡ�����ļ�, ������ �û� �����е�Ȩ�� �� ������ 
 *   	2. �ж� �û�Ҫ���ʵ�Ŀ����Դ�Ƿ���ҪȨ��,
 *   		����ҪȨ��: ֱ�ӷ���
 *   		��ҪȨ��: ����3
 *   	3. �ж� �Ƿ��Ѿ���¼
 *   		û�е�¼:  -- ������ת�� ��¼�Ľ���
 *   		�Ѿ���¼:
 *   			����¼���û��Ľ�ɫ��ʲô 
 *   				�� ���ʵ���Դ�Ƿ����㹻��Ȩ��
 *   					��Ȩ��: ֱ�ӷ���
 *   					û��Ȩ��: �׸��쳣��ȥ--��ʾ��û��Ȩ�� 
 * 
 */
public class PrivilegesFilter implements Filter {

	//���� ���� list ���� �����û��� Ȩ����Ϣ
	private List adminList = new ArrayList();
	private List userList = new ArrayList();
	
	
	//���û���Ȩ�� ��ȡ����,�ŵ������� list ��ȥ
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		try {
			String adminPath = PrivilegesFilter.class.getClassLoader().getResource("admin.txt").getFile();
			String userPath = PrivilegesFilter.class.getClassLoader().getResource("user.txt").getFile();
			
			BufferedReader br1 = new BufferedReader(new FileReader(adminPath));
			String line1 = null;
			while( (line1 = br1.readLine())!=null){
				
				adminList.add(line1);
			}
			
			BufferedReader br2 = new BufferedReader(new FileReader(userPath));
			String line2 = null;
			while( (line2 = br2.readLine())!=null){
				
				userList.add(line2);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		// ��Ȩ�޵� ���� 
		
		// ���Ҫ���ʵ�Ŀ����Դ��·��
		String path = request.getRequestURI().substring(request.getContextPath().length());
		
		if(adminList.contains(path)||userList.contains(path)){
			
			//���� ��˵�� ���ʵ�Ŀ����Դ�� ��ҪȨ�޵� 
			
			// �ж��Ƿ��Ѿ���¼--- ���Դ�session ��ȡ�� ��¼���û�
			if(request.getSession().getAttribute("loginUser")==null){
				
				// Ӧ�����û��ȵ�¼, ��� ����˭ 
				request.setAttribute("message", "�Բ���,����ʵ���Դ��ҪȨ��,���ȵ�¼..");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				
				//˵���Ѿ���¼
				//�ж�  ��˭,Ȼ�� �ж��Ƿ����Ȩ��
				User loginUser = (User) request.getSession().getAttribute("loginUser");
				if(loginUser.getRole().equals("admin")){
					
					//˵���ǹ���Ա��¼�� 
					if(adminList.contains(path)){
						
						//���� ��˵����Ȩ��
						chain.doFilter(request, response);
					}else{
						//˵��û��Ȩ��
						throw new RuntimeException("�Բ���,���ǹ���Ա, û�����Ȩ��...");
					}
				}
				
				if(loginUser.getRole().equals("user")){
					
					if(userList.contains(path)){
						
//						˵����Ȩ��
						chain.doFilter(request, response);
					}else{
						
						//ûȨ��
						throw new RuntimeException("�Բ���,���� ��ͨ�û� , û�����Ȩ��...");
					}
				}
			}
		}else{
			
			//˵�����ʵ���Դ�ǲ���ҪȨ�޵� , ֱ�ӷ���
			chain.doFilter(request, response);
		}
		
		
	}

	@Override
	public void destroy() {

	}

}
