package com.jun.plugin.project.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @createTime 2021年02月01日 14:00:00
 */
@Component
public class MyInterceptor2 implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MyInterceptor2.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       logger.info("------------------ preHandle");
       request.setAttribute("startTime",System.currentTimeMillis());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.info("------------------ postHandle");
        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        logger.info("请求处理时间： " + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        logger.info("------------------ after");
    }
}