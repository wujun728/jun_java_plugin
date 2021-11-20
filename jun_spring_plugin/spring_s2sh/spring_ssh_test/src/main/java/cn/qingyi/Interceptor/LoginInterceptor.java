package cn.qingyi.Interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * LoginInterceptor类的描述
 *
 * @author Wujun
 * @version V1.0
 * @ClassName: LoginInterceptor
 * @Package cn.qingyi.Interceptor
 * @Description: 这里用一句话描述这个类的作用
 * @date 2017/3/20 9:35
 */
public class LoginInterceptor implements Interceptor {

    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;


    @Override
    public void destroy() {
        System.out.println("登陆验证拦截器完毕!");
    }

    @Override
    public void init() {
        System.out.println("登陆验证拦截器启动!");
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        System.out.println("---登陆开始验证---");
        System.out.println("username:"+request.getParameter("username"));
        System.out.println("password:"+request.getParameter("password"));
        return invocation.invoke();
    }
}
