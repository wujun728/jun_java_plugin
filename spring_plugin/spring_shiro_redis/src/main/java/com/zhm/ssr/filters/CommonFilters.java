package com.zhm.ssr.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zhm on 2015/7/10.
 */
public class CommonFilters implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        request.setAttribute("cpath",request.getContextPath());
        chain.doFilter(req,resp);
    }

    public void destroy() {

    }
}
