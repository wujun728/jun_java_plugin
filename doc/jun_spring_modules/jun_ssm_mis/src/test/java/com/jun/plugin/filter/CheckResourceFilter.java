package com.jun.plugin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

/**
 * 权限过滤器
 * @author Wujun
 * @createTime   2011-9-3 上午08:56:27
 */
public class CheckResourceFilter implements Filter {
	private String[] ignore;
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {/*
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String basePath = request.getContextPath()+"/";  //例如： "/MyOA"
		String url = request.getRequestURI();   //例如：/MyOA/UserAction!findAllUsers.do 或 /MyOA/grantResource.jsp
		url = url.substring(basePath.length());
		if(url.indexOf(".jsp") == -1){
			if(url.indexOf(".") != -1){
				url = url.substring(0,url.indexOf(".")+3);
			}
		} else{
			if(url.indexOf("?") != -1){
				url = url.substring(0,url.indexOf("?"));
			}
		}
		
		//System.out.println("试图要访问的url:" + url);
		User currentUser = (User)request.getSession().getAttribute("");
		//如果存在于忽略资源列表里，则不需要权限验证
		if(checkIgnore(url)){
			chain.doFilter(req, resp);
		} else{
			//如果未登录,则重定向到登录页面
			if(null == currentUser){
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			} else{
				//如果是超级管理员，则不需要权限验证
				if(currentUser.getLoginName().equals("admin")){
					chain.doFilter(req, resp);
				} else{
					boolean hasPrivilege = false;
					for(Role role : currentUser.getRoles()){
						for(Resource resource : role.getResources()){
							String u = resource.getUrl().substring(0,resource.getUrl().indexOf(".")+3);
							//System.out.println("系统对应权限url:" + u);
							//System.out.println("用户请求url:" + url);
							if(url.equals(u)){
								hasPrivilege = true;
								break;
							}
						}
					}
					if(hasPrivilege){
						chain.doFilter(req, resp);
					} else{
						//重定向到登录页面
						//response.sendRedirect("login.jsp");
						response.setHeader("Pragma","No-cache"); 
						response.setHeader("Cache-Control","no-cache"); 
						response.setDateHeader("Expires", 0);
						response.getWriter().write(
						"<script type=\"text/javascript\">window.open(\"noPermission.jsp\", \"\", \"height=768,width=1024,top=0,left=0,toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=yes,status=yes\");parent.window.opener=null; parent.window.open(\"\",\"_parent\"); parent.window.close();</script>"
						);
					}
				}
			}
		}
	*/}

	public void init(FilterConfig config) throws ServletException {
		ignore = new String[]{
			"","login.jsp","userAction!login.do","top.jsp","left.jsp","middle.jsp",
			"main.jsp","welcome.jsp","noPermission.jsp","roleAction!findAllRoles.do",
			"resourceAction!createUserResourcesJson.do"
		};
	}
	
	/**
	 * 检测某个url是否存在于忽略列表里
	 * 存在则放行，否则拦截
	 */
	private boolean checkIgnore(String url){
		boolean flag = false;
		for(int i = 0; i < ignore.length; i++){
			if(url.equals(ignore[i])){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
