package com.jun.webservlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;  
  
//asyncSupported=true 对应filter也需要定义asyncSupported=true  
@WebFilter(urlPatterns={"/*"}, filterName="my3Filter", asyncSupported=true)  
@WebInitParam(name="a", value="valuea")  
public class My3Filter implements Filter{  
  
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest arg0, ServletResponse arg1,  
            FilterChain arg2) throws IOException, ServletException {  
        System.out.println("servlet 3 filter");  
        arg2.doFilter(arg0, arg1);  
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
        System.out.println("servlet 3 filter init");  
    }  
  
}  