package com.java1234.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class AccessFilter extends ZuulFilter{

	Logger logger=Logger.getLogger(AccessFilter.class);
	
	 /**
     * 判断该过滤器是否要被执行
     */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
     * 过滤器的具体执行逻辑
     */
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String parameter = request.getParameter("accessToken");
        logger.info(request.getRequestURL().toString()+" 请求访问");
        if(parameter==null){
            logger.error("accessToken为空！");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("{\"result\":\"accessToken is empty!\"}");
            return null;
        }
        //  token判断逻辑
        logger.info(request.getRequestURL().toString()+" 请求成功");
        return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	/**
     * 过滤器的执行顺序
     */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
