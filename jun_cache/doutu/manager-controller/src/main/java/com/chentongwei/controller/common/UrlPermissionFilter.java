package com.chentongwei.controller.common;

import com.alibaba.fastjson.JSON;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Url权限过滤器，Session验证
 *
 * @author TongWei.Chen 2017-06-10 21:04
 **/
//@WebFilter(filterName = "/UrlPermissionFilter", urlPatterns = "/*")
public class UrlPermissionFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(UrlPermissionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //登录页面不过滤
        if(Objects.equals(request.getServletPath(), "/common/user/login")
                    || Objects.equals(request.getServletPath(), "/common/captcha/getCaptcha")) {
            LOG.info("登录页面不过滤");
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            if(null == session.getAttribute("user")) {
                //跳转到login接口
                LOG.info("请求URL[{}]失败,请先登录", request.getServletPath());
                Result result = new Result();
                result.setCode(ResponseEnum.LOGIN.getCode());
                result.setMsg(ResponseEnum.LOGIN.getMsg());
                response.getWriter().println(JSON.toJSONString(result));
            } else {
                LOG.info("登录过了不过滤");
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
