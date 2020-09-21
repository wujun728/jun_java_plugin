package com.chentongwei.controller.common;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.entity.common.io.SysLogIO;
import com.chentongwei.entity.common.vo.UserVO;
import com.chentongwei.service.common.ISysLogService;
import com.chentongwei.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * 系统日志拦截器
 *
 * @author TongWei.Chen 2017-06-01 10:06:46
 */
@Component
public class SysLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 当*Controller的业务方法里出现了异常，此方法就不会被执行。
     *
     * 后处理回调方法，实现处理器的后处理（但在渲染视图之前），此时我们可以通过modelAndView（模型和视图对象）对模型数据进行处理或对视图进行处理，modelAndView也可能为null。
     *
     * @param request request
     * @param response response
     * @param handler handler
     * @param modelAndView modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (! (handler instanceof HandlerMethod)) {
            return ;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //获取类注解
        Class<? extends Object> clazz = handlerMethod.getBean().getClass();
        CategoryLog categoryLog = clazz.getAnnotation(CategoryLog.class);
        if (null == categoryLog) {
            return ;
        }
        String menu = categoryLog.menu();
        String subMenu = categoryLog.subMenu();

        //获取方法注解
        Method method = handlerMethod.getMethod();
        DescLog descLog = method.getAnnotation(DescLog.class);
        if (null == descLog) {
            return ;
        }
        String descLogValue = descLog.value();

        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        //记录日志
        SysLogIO sysLog = new SysLogIO();
        sysLog.setOperatorId(user == null ? -1L : user.getId());
        sysLog.setOperator(user == null ? "admin" : user.getLoginName());
        sysLog.setMenu(menu);
        sysLog.setSubMenu(subMenu);
        sysLog.setContent(descLogValue);
        sysLog.setClientIp(IPUtil.getIP(request));
        sysLogService.save(sysLog);
    }
}
