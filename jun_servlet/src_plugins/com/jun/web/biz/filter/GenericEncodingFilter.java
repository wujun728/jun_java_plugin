package com.jun.web.biz.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
/*
 *  ͨ�õĽ��ȫվ�� ���������������Ĺ����� 
 *  	
 *  	�������ʱ, ������� ����������ʽ ֻ�� get ��post 
 *  
 *  	
 *  	˼�� : ���  ������� :
 *  
 *  	request.getParameter("name");  --- ������ 
 *  	request.getParameterValues("hobbies");  ----������ ���� 
 *  	request.getParameterMap();   --- Map<String, String[]> getParameterMap
 *  
 *  	Ҫ��ò���, �͵� ʹ�� ���ϵ��������� , 
 *  	
 *  //	�����Ľ����, ��Ҫȥ ��д ������������, ���� ���� getParameterMap �а������� ���е�����Ĳ��� ��Ϣ, ���� 
 *  	���� �� ���������� ���� ,��������һ�� ����, ���ǵ����� �������  getParameterMap �����оͿ�����. ����������
 *  	�������� ��������ʱ, ֻ��Ҫȥmap ��ȡ�� ��Ӧ�Ĳ���ֵ�Ϳ��� �� . 
 *  
 * 
 */
public class GenericEncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setContentType("text/html;charset=utf-8");
		
		// Ҫ������� --- �ܲ���  �Զ��� һ����װ��, ȥ��װ��� request ����, 
		
		MyHttpServletRequestWrapper myrequest = new MyHttpServletRequestWrapper(request);
		
		
		chain.doFilter(myrequest, response);  //���� , Ŀ����Դ��õ� ִ�� 
		
	}
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	

	@Override
	public void destroy() {

	}

}

class MyHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private HttpServletRequest request;

	public MyHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		
		Map<String,String[]> map = getParameterMap();
		//��ö�Ӧ  values ���� 
		String[] values = map.get(name);
		
		if(values!=null){
			
			//��������ĵ�һ��Ԫ�� 
			return values[0];
		}
		return null;
	}
	
	@Override
	public String[] getParameterValues(String name) {
		Map<String,String[]> map = getParameterMap();
		//��ö�Ӧ  values ���� 
		String[] values = map.get(name);
		return values;
	}
	
	//Ĭ�ϵ���false, ��ʾû�� �� ���� �� 
	private boolean hasBeenEncoded= false;
	
	@Override
	public Map getParameterMap() {
		
		//ֻ�� get ��post 
		
		//������� ��ʽ
		String method = request.getMethod();
		
		if("post".equalsIgnoreCase(method)){
			
			//��� ����,��ʾ ��post����ʽ
			
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			//˵����get 
			
			//�õ� ���е������ map 
			Map<String,String[]> map = request.getParameterMap();
			
			//���� map 
			Set<String> keys = map.keySet();
			
			// ����ı��� ԭ����, ���� �� for ѭ������߼� ���ظ��ĵ�����, �����������, Ӧ����ֻ ����һ��, �ͻ�������
			// ���ȷ������ ��forѭ����� ���� ֻ�� ����һ��  
			
			
			//���� ���� ����ֵ, ʵ�� ���� ֻ�� ����һ��, ��ʵ�ʿ���������,�ǳ� ���õ�.  (�����ԵĴ���)
			if(!hasBeenEncoded){
				hasBeenEncoded = true;
			
				for (String key : keys) {
					//����� 
					String[] values = map.get(key);
					for (int i = 0; i < values.length; i++) {
						
						try {
							System.out.println("֮ǰ: " + values[i]);
							values[i] = new String(values[i].getBytes("ISO8859-1"),"UTF-8");
							System.out.println("֮��: " + values[i]);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return map;
		}
		return super.getParameterMap();
	}
	
}

