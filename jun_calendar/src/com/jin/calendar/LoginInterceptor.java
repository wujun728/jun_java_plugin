package com.jin.calendar;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 * 
 * @author Jin
 * @datetime 2014/9/26 下午4:15:13
 */
public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		String user = ai.getController().getSessionAttr("username");
		if(user == null){
			ai.getController().redirect("/");
		}else {
			ai.invoke();
		}
	}

}
