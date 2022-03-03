package com.feri.fyw.filter;

import com.feri.fyw.config.SystemConfig;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

/**
 * @program: FengYuWisdom
 * @description: 自定义拦截器 只拦截接口
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:22
 */
public class LoginAuthFilter implements HandlerInterceptor {
    //白名单，不需要拦截的内容
    private HashSet<String> whiteUrl=new HashSet<>();
    public LoginAuthFilter(){
        whiteUrl.add("login.html");
        whiteUrl.add("login.do");
        whiteUrl.add("/api/captch/create.do");
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取当前访问的地址
        String url=request.getRequestURI();
        System.err.println(url);
        //2.校验地址是否属于白名单
        for(String s:whiteUrl){
            if(url.endsWith(s)){
                return true;
            }
        }
        //3.校验是否登陆
        if(request.getSession().getAttribute(SystemConfig.CURR_USER)!=null){
            //已登录，放行
            return true;
        }else {
            //未登录，拦截，同时跳转到登陆页面
            response.sendRedirect("/login.html");
            return false;
        }
    }
}
