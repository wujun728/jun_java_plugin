package com.company.project.configurer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultCode;
import com.company.project.service.HttpSessionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author wenbin
 * @since 2019/10/30 15:29
 * <p>登陆拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSessionService httpSession;

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用户当前session信息
        JSONObject currentSession;
        //拦截接口
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        //token为空返回
        if (StringUtils.isBlank(token)) {
            Result result = new Result();
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("token不能为空").setSuccess(false);
            responseResult(response, result);
            return false;
        }//  校验并解析token，如果token过期或者篡改，则会返回null
        currentSession = httpSession.getCurrentSession();
        if (null != currentSession) {
            return true;
        } else {
            Result result = new Result();
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("用户未登陆!").setSuccess(false);
            responseResult(response, result);
            return false;
        }
    }


    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
