package org.itcast.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.itcast.demo.exception.MyException;
import org.itcast.demo.util.ConstUtils;
import org.itcast.demo.util.ResponseCode;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description
 * @auther: CDHong
 * @date: 2019/6/27-13:57
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Object obj = request.getSession().getAttribute(ConstUtils.CURRENT_LOGIN_USER);
        String requestURI = request.getRequestURI();
        if(Objects.nonNull(obj)){
            return true;
        }
        log.debug("请求路径：【{}】被拦截了",requestURI);
        throw new MyException(ResponseCode.NEED_LOGIN);
        //response.sendRedirect("/login");
        //return false;
    }
}
