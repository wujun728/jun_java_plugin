package com.jun.plugin.picturemanage.conf;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 16:41
 */
public class CheckConfigFullInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!InitState.IS_INIT || StringUtils.isBlank(Constant.ROOT_DIR)) {
            //进行初始化的操作
            response.sendRedirect("/init/setup");
            return false;
        }

        return true;
    }
}
