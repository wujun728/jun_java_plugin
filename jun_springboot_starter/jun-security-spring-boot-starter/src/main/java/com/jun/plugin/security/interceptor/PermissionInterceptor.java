package com.jun.plugin.security.interceptor;

import com.jun.plugin.security.exception.NoPermissionException;
import com.jun.plugin.security.interceptor.holder.AuthenticeHolder;
import com.jun.plugin.security.interceptor.holder.PermissionHolder;
import com.jun.plugin.security.interceptor.holder.RoleHolder;
import com.jun.plugin.security.interfaces.PermissionInfoInterface;
import com.jun.plugin.security.util.AuthUtil;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 用户权限验证拦截器
 *
 * @version  2020-03-22-11:23
 **/
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    /**
     * 权限角色信息
     */
    private PermissionInfoInterface permissionInfoInterface;

    public PermissionInfoInterface getPermissionInfoInterface() {
        return this.permissionInfoInterface;
    }

    public void setPermissionInfoInterface(PermissionInfoInterface permissionInfoInterface) {
        this.permissionInfoInterface = permissionInfoInterface;
    }

    public PermissionInterceptor(PermissionInfoInterface permissionInfoInterface) {
        this.setPermissionInfoInterface(permissionInfoInterface);
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

        Method method = ((HandlerMethod) handler).getMethod();
        Object loginId = AuthenticeHolder.getLoginId();
        Set<String> roleSet = this.getPermissionInfoInterface().getRoleSet(loginId);
        Set<String> permissionSet = this.getPermissionInfoInterface().getPermissionSet(loginId);

        RoleHolder.setRoleSet(roleSet);
        PermissionHolder.setPermissionSet(permissionSet);

        if (AuthUtil.checkPermission(method, permissionSet) && AuthUtil.checkRole(method, roleSet)) {
            return super.preHandle(request, response, handler);
        } else {
            // 权限和角色校验不通过
            // 直接抛出异常的话，就不需要return false了
            throw new NoPermissionException();
        }
    }


    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // logger.info("PermissionInterceptor -- postHandle -- 执行了");
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        // logger.info("PermissionInterceptor -- afterCompletion -- 执行了");
        RoleHolder.clearRoleSet();
        PermissionHolder.clearPermissionSet();
    }

}
