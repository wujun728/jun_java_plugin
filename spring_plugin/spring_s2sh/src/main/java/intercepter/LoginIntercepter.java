package intercepter;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginIntercepter extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation in) throws Exception {
		ActionContext cxt=in.getInvocationContext();
		Map s=cxt.getSession();
		String userid=(String)s.get("username");
		if(userid!=null)
		return in.invoke();
		else
		return Action.LOGIN;
	}

}
