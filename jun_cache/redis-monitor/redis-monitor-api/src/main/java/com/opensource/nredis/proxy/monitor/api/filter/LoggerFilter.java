/**
 * 
 */
package com.opensource.nredis.proxy.monitor.api.filter;


import com.alibaba.fastjson.JSONObject;
import com.opensource.nredis.proxy.monitor.api.utils.IpUtil;
import com.opensource.nredis.proxy.monitor.platform.ResponseObject;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 */
public class LoggerFilter implements Filter {

    private Logger logger = Logger.getLogger(LoggerFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("---------LoggerFilter start----------");
    }

    /**
     * 接口执行时间
     * 参数输出
     * 错误的统一处理
     *
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        ResponseObject resObject = new ResponseObject();
        PrintWriter out = null;
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        if(req.getRequestURI().contains(".")){//静态资源过滤
        	 chain.doFilter(request, response);
        	 return;
        }
        long t1 = System.currentTimeMillis();
        Map<String, String[]> params = request.getParameterMap();
        StringBuilder queryString = new StringBuilder();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                queryString.append(key);
                queryString.append("=");
                queryString.append(values[i]);
                queryString.append("&");
            }
        }

        if (queryString.length() > 1) {// 去掉最后一个 '&'
            queryString.deleteCharAt(queryString.length() - 1);
        }

        String ip = IpUtil.getIpAddr(httprequest);

        try {

            chain.doFilter(request, response);
        } catch (Exception e) {
            //logger.error(e);
            httpresponse.setStatus(200);
            httpresponse.setCharacterEncoding("UTF-8");
            resObject.setStatus(500);
            resObject.setMessage("服务器内部错误,错误信息:"+e.getMessage()+",原因:"+e.getCause());
            resObject.setTcost("" + (System.currentTimeMillis() - t1));
            String responseJsonString = JSONObject.toJSONString(resObject);
            out = httpresponse.getWriter();
            out.print(responseJsonString);
        } finally {
            long t2 = System.currentTimeMillis();

            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append("\n================================BEGIN=====================================");
            logBuilder.append("\n ip:[" + ip + "]");
            logBuilder.append("\nRequestURI:" + req.getRequestURI());
            logBuilder.append("\nRequestParams:" + queryString);
            logBuilder.append("\nFinished with time: " + (t2 - t1) + "ms");
            logBuilder.append("\n*******************************END**************************************");
            logger.info(logBuilder);
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }

    public void destroy() {

    }
}
