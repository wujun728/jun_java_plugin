package cn.qingyi.Filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆验证过滤器
 *
 * @author Wujun
 * @version V1.0
 * @ClassName: LoginFilter
 * @Package cn.qingyi.Filter
 * @Description: 登陆验证过滤器
 * @date 2017/3/20 14:49
 * @Note 此处不能用注解原因:过滤所有*.action会在struts过滤器后,不起作用,需在web.xml中配置在Struts过滤器之前
 */
//@WebFilter(dispatcherTypes = {DispatcherType.REQUEST }, urlPatterns = { "*.action" })
public class LoginFilter extends StrutsPrepareAndExecuteFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        //登陆验证拦截器
        System.out.println("登陆验证过滤器----"+"URI:"+request.getRequestURI());

        //判断是否为登陆action
        if (!request.getRequestURI().contains("login.action")){
            HttpSession session = request.getSession();
            if ("true".equals(session.getAttribute("loginStatus"))){//已登陆
            }else{//未登录,返回登陆页面
                HttpServletResponse response = (HttpServletResponse)res;
                response.sendRedirect(request.getContextPath());
                return;
            }
        }
        super.doFilter(req, res, chain);
    }
}
