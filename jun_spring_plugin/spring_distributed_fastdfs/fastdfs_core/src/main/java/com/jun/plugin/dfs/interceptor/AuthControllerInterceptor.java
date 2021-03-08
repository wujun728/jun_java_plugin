package com.jun.plugin.dfs.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.dfs.base.BaseController;
import com.jun.plugin.dfs.base.ErrorCodeEnum;
import com.jun.plugin.dfs.base.spring.SpringContextHolder;
import com.jun.plugin.dfs.core.service.AppInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthControllerInterceptor extends BaseController implements HandlerInterceptor {

    private AppInfoService appInfoService = SpringContextHolder.getBean(AppInfoService.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String appKey = request.getHeader(HEADER_APP_KEY);
        String timestamp = request.getHeader(HEADER_TIMESTAMP);
        String sign = request.getHeader(HEADER_SIGN);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(sign)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write(getResponseByCode(ErrorCodeEnum.AUTH_PARAM_ERROR).getBytes(StandardCharsets.UTF_8));
            response.getOutputStream().flush();
            return false;
        }
        ErrorCodeEnum eCode = appInfoService.checkAuth(appKey, timestamp, sign);
        if (eCode != ErrorCodeEnum.OK) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write(getResponseByCode(eCode).getBytes(StandardCharsets.UTF_8));
            response.getOutputStream().flush();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
