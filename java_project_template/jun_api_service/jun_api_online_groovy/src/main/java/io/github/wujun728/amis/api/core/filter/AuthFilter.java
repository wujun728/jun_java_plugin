package io.github.wujun728.amis.api.core.filter;

import com.alibaba.fastjson2.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class AuthFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/admin/login")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        System.out.println("path:" + path);

        String sessionId = req.getSession().getId();
        System.out.println("sessionId：" + sessionId + "  req.method:" + req.getMethod());
        //filterChain.doFilter(servletRequest, servletResponse);

        //放行options请求, 因为浏览器的跨域验证，通过options请求发送
        if(req.getMethod().equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (allowedPath) {
            System.out.println("这里是不需要处理的url进入的方法");
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            System.out.println("这里是需要处理的url进入的方法");
            Object currentUser = req.getSession().getAttribute("currentUser");
            if(currentUser != null){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                resp.setCharacterEncoding("UTF-8");
                resp.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                resp.addHeader("Access-Control-Allow-Credentials","true");
                resp.setContentType("application/json; charset=utf-8");
                PrintWriter out = resp.getWriter();
                JSONObject res = new JSONObject();
                res.put("code", -101);
                res.put("restInfo", "登录已过期，请重写登录");
                out.append(res.toString());
                out = resp.getWriter();
            }
        }
    }
}
