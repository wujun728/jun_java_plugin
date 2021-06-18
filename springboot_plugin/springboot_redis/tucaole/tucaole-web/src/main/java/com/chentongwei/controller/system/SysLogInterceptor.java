package com.chentongwei.controller.system;

import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.enums.constant.RedisEnum;
import com.chentongwei.common.util.IPUtil;
import com.chentongwei.controller.user.UserController;
import com.chentongwei.core.system.biz.SysLogBiz;
import com.chentongwei.core.system.entity.io.SysLogIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 系统日志拦截器
 */
@Component
public class SysLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SysLogBiz sysLogBiz;
    @Autowired
    private IBasicCache basicCache;

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

        //获取方法注解
        Method method = handlerMethod.getMethod();
        DescLog descLog = method.getAnnotation(DescLog.class);
        if (null == descLog) {
            return ;
        }
        String descLogValue = descLog.value();

//        记录日志
        SysLogIO sysLog = new SysLogIO();
        sysLog.setMenu(menu);
        sysLog.setContent(descLogValue);
        sysLog.setClientIp(IPUtil.getIP(request));
        /*
            切记，切记。
            如下功能不能使用拦截器记录操作日志，
            登录、注册、激活用户
            因为token是登录之后存到redis的，所以登录的时候我是不知道你的token，不能直接从redis中取出登录用户信息，所以只记录一个email，不记录id
         */
        if (clazz.equals(UserController.class)
                && ("login".equals(method.getName())
                || "regist".equals(method.getName())
                || "activeUser".equals(method.getName()))) {
            sysLog.setOperatorId(-1L);
        } else {
            //从Redis中取出当前登录用户
            sysLog.setOperatorId(request.getHeader("userId") == null ? -1L : Long.parseLong(request.getHeader("userId")));
        }
        sysLogBiz.save(sysLog);
    }
}
