package com.luo.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class Base2Action extends ActionSupport{

	private static final long serialVersionUID = 1L;
	public ActionContext actionContext=ActionContext.getContext();
	public HttpServletRequest request=(HttpServletRequest) actionContext.get(ServletActionContext.HTTP_REQUEST);
	public HttpServletResponse response=(HttpServletResponse) actionContext.get(ServletActionContext.HTTP_RESPONSE);
	public Map<String,Object> session=actionContext.getSession();//使用SessionMap即可做所有操作，无需使用HttpServletSession
	public Map<String,Object> application=actionContext.getApplication();
	
}
