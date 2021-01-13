package com.jun.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName="EncodingFilter" ,urlPatterns={"/*"},
initParams={ @WebInitParam(name="name",value="jun"),  @WebInitParam(name="age",value="20") },asyncSupported=true ) 
public class EncodingFilter implements Filter { 
	
	private FilterConfig conf;
	
	public void init(FilterConfig conf) throws ServletException {
		this.conf=conf;
		System.err.println("过虑器初始化了:"+this);
		String name = conf.getInitParameter("name");
		System.err.println("name is:"+name);
		System.err.println("----------------------------");
	 	Enumeration<String> en= conf.getInitParameterNames();
		while(en.hasMoreElements()){
			String paramName = en.nextElement();
			String val = conf.getInitParameter(paramName);
			System.err.println(paramName+"="+val);
		}
	}
	
	public void destroy() {
		System.err.println("过虑器dead.."+this);
	}
	
	public void doFilter_1(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		
		HttpServletRequest req = (HttpServletRequest) request;
		

		if(req.getMethod().equals("GET")) {
		} else if(req.getMethod().equals("POST")) {
			chain.doFilter(request, response);
		}
	}
	
//	@Override
	public void doFilter_2(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		// ת��
		final HttpServletRequest request = (HttpServletRequest) req;    
		HttpServletResponse response = (HttpServletResponse) res;
		
		// һ�����?��ҵ��
		request.setCharacterEncoding("UTF-8");					// POST�ύ��Ч
		response.setContentType("text/html;charset=UTF-8");
		
		/*
		 * ����GET�������룬����Ϊ��request.getParameter�����ڲ�û�н����ύ��ʽ�жϲ����?
		 * String name = request.getParameter("userName");
		 * 
		 * �������ָ���ӿڵ�ĳһ���������й�����չ������ʹ�ô���!
		 *      ��request����(Ŀ�����)�������������
		 */
		HttpServletRequest proxy =  (HttpServletRequest) Proxy.newProxyInstance(
				request.getClass().getClassLoader(), 		// ָ����ǰʹ�õ��ۼ�����
				new Class[]{HttpServletRequest.class}, 		// ��Ŀ�����ʵ�ֵĽӿ�����
				new InvocationHandler() {					// �¼�������
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						// ���巽������ֵ
						Object returnValue = null;
						// ��ȡ������
						String methodName = method.getName();
						// �жϣ���getParameter��������GET�ύ���Ĵ���
						if ("getParameter".equals(methodName)) {
							
							// ��ȡ�������ֵ�� <input type="text" name="userName">��
							String value = request.getParameter(args[0].toString());	// ����Ŀ�����ķ���
							
							// ��ȡ�ύ��ʽ
							String methodSubmit = request.getMethod(); // ֱ�ӵ���Ŀ�����ķ���
							
							// �ж������GET�ύ����Ҫ����ݽ��д���  (POST�ύ�Ѿ��������)
							if ("GET".equals(methodSubmit)) {
								if (value != null && !"".equals(value.trim())){
									// ����GET����
									value = new String(value.getBytes("ISO8859-1"),"UTF-8");
								}
							} 
							return value;
						}
						else {
							returnValue = method.invoke(request, args);
						}
						
						return returnValue;
					}
				});
		
		chain.doFilter(proxy, response);		// ����������
	}


	/*public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		StackTraceElement[] ste = new Throwable().getStackTrace();
        StringBuffer CallStack = new StringBuffer();
        for (int i = 0; i < ste.length; i++) {
            CallStack.append(ste[i].toString() + " | ");
            if (i > 1)
                break;
        }
        ste = null;
        System.out.println("执行路径：" + CallStack.toString()+"  "+request.getCharacterEncoding());
        
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}*/
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;    
		HttpServletResponse response = (HttpServletResponse) res;
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		System.err.println("1：正在执行过虑："+this+","+request);
		String name = conf.getInitParameter("name");
		System.err.println("doFilter :  name is:"+name);
		
		if(request.getSession().getAttribute("ip")==null){
			request.getSession().setAttribute("ip",req.getRemoteAddr());
		}
		
		
		HttpServletRequest proxy =  (HttpServletRequest) Proxy.newProxyInstance(
				request.getClass().getClassLoader(), 
				new Class[]{HttpServletRequest.class},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						Object returnValue = null;
						String methodName = method.getName();
						if ("getParameter".equals(methodName)) {
							String value = request.getParameter(args[0].toString());
							String methodSubmit = request.getMethod();
							if ("GET".equals(methodSubmit)) {
								if (value != null && !"".equals(value.trim())){
									value = new String(value.getBytes("ISO8859-1"),"UTF-8");
								}
							} 
							return value;
						}
						else {
							returnValue = method.invoke(request, args);
						}
						
						return returnValue;
					}
				});
		
		
//		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//		HttpServletRequest MyRequest6677 = new MyRequest6677(httpServletRequest);
//		chain.doFilter(MyRequest6677, response);
//		
		chain.doFilter(proxy, response);
		System.err.println("3：目标组件，执行完成了...");
	}

}

//声明包装类
class MyRequest6677 extends HttpServletRequestWrapper {
	private String[] ss;
	
	public MyRequest6677(HttpServletRequest request) {
		//读取数据库，将读取的数据放到缓存
		super(request);
		ss=new String[]{"SB","江泽民","小胡"};
	}

	// 增强getParamter
	@Override
	public String getParameter(String name) {
		String val = super.getParameter(name);
		try {
			val = new String(val.getBytes("ISO-8859-1"),
					super.getCharacterEncoding());
			for(String s:ss){
				val = val.replace(s, "****");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val;
	}
}


/*
//自定义request对象
class MyNewRequest11 extends HttpServletRequestWrapper {
	private HttpServletRequest request;
	private boolean hasEncode;
	public MyNewRequest11(HttpServletRequest request) {
		super(request);// super必须写
		this.request = request;
	}
	// 对需要增强方法 进行覆盖
	@Override
	public Map getParameterMap() {
		// 先获得请求方式
		String method = request.getMethod();
		if (method.equalsIgnoreCase("post")) {
			// post请求
			try {
				// 处理post乱码
				request.setCharacterEncoding("utf-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if (method.equalsIgnoreCase("get")) {
			// get请求
			Map<String, String[]> parameterMap = request.getParameterMap();
			if (!hasEncode) { // 确保get手动编码逻辑只运行一次
				for (String parameterName : parameterMap.keySet()) {
					String[] values = parameterMap.get(parameterName);
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							try {
								// 处理get乱码
								values[i] = new String(
										values[i].getBytes("ISO-8859-1"),
										"utf-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
				}
				hasEncode = true;
			}
			return parameterMap;
		}

		return super.getParameterMap();
	}

	@Override
	public String getParameter(String name) {
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);
		if (values == null) {
			return null;
		}
		return values[0]; // 取回参数的第一个值
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> parameterMap = getParameterMap();
		String[] values = parameterMap.get(name);
		return values;
	}

}*/