package com.jun.plugin.security.interceptor;

import com.jun.plugin.security.exception.UnAuthorizedException;
import com.jun.plugin.security.interceptor.holder.AuthenticeHolder;
import com.jun.plugin.security.provider.AuthProvider;
import com.jun.plugin.security.util.AuthUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 用户会话验证拦截器
 *
 * @version 2020-03-22-11:23
 **/
public class AuthenticeInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存储会话的接口
     */
    private AuthProvider authProvider;

    public AuthProvider getAuthProvider() {
        return this.authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }


    public AuthenticeInterceptor(AuthProvider authProvider) {
        this.setAuthProvider(authProvider);
    }


    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        // 判断请求类型，如果是OPTIONS，直接返回
        String options = HttpMethod.OPTIONS.toString();
        if (options.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return super.preHandle(request, response, handler);
        }

        // 检查是否忽略会话验证
        Method method = ((HandlerMethod) handler).getMethod();
        if (AuthUtil.checkIgnore(method)) {
            return super.preHandle(request, response, handler);
        }

        // 第一步、先从请求的request里获取传来的token值，并且判断token值是否为空
        String token = this.getAuthProvider().getToken(request);
        if (StringUtils.isEmpty(token)) {
            // 直接抛出异常的话，就不需要return false了
            throw new UnAuthorizedException();
        }

        // 第二步、再判断此token值在会话存储器中是否存在，存在的话说明会话有效，并刷新会话时长
        if (!this.getAuthProvider().checkToken(token)) {
            throw new UnAuthorizedException();
        } else {
            this.getAuthProvider().refreshToken(token);
            // 存入LoginId，以方便后续使用
            AuthenticeHolder.setLoginId(this.getAuthProvider().getLoginId(token));
            // 合格不需要拦截，放行
            return super.preHandle(request, response, handler);
        }
    }


    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        AuthenticeHolder.clearLoginId();
    }
}
