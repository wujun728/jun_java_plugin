package com.cosmoplat.common.config;

import com.alibaba.fastjson.JSON;
import com.cosmoplat.common.exception.code.BaseResponseCode;
import com.cosmoplat.common.utils.DataResult;
import com.cosmoplat.service.sys.HttpSessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private HttpSessionService httpSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        //token为空返回
        if (StringUtils.isBlank(token)) {
            DataResult result = DataResult.fail(BaseResponseCode.TOKEN_ERROR);
            responseResult(response, result);
            return false;
        }
        //  校验并解析token，如果token过期或者篡改，则会返回null
        if (httpSession.getCurrentSession() == null) {
            DataResult result = DataResult.fail(BaseResponseCode.TOKEN_ERROR);
            responseResult(response, result);
            return false;
        }
        // INFO 判断用户状态
        return true;
    }


    private void responseResult(HttpServletResponse response, DataResult result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
