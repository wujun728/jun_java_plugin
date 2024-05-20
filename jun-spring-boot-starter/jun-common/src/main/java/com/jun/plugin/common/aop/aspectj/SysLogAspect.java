package com.jun.plugin.common.aop.aspectj;

import com.alibaba.fastjson2.JSON;
import com.jun.plugin.common.aop.annotation.LogAnnotation;
import com.jun.plugin.common.util.HttpRequestUtil;
import com.jun.plugin.common.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    /**
     * 此处的切点是注解的方式
     * 只要出现 @LogAnnotation注解都会进入
     */
    @Pointcut("@annotation(com.jun.plugin.common.aop.annotation.LogAnnotation)")
    public void logPointCut() {

    }

    /**
     * 环绕增强,相当于MethodInterceptor
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        try {
            saveSysLog(point, time);
        } catch (Exception e) {
            log.error("sysLog,exception:{}", e, e);
        }

        return result;
    }

    /**
     * 把日志保存
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //SysLog sysLog = new SysLog();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if (logAnnotation != null) {
            //注解上的描述
            //sysLog.setOperation(logAnnotation.title() + "-" + logAnnotation.action());
            log.info("Operation Info:"+logAnnotation.title() + "-" + logAnnotation.action());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        //sysLog.setMethod(className + "." + methodName + "()");
        log.info("请求{}.{}耗时{}毫秒", className, methodName, time);
        String params = null;
        try {
            //请求的参数
            Object[] args = joinPoint.getArgs();

            if (args.length != 0) {
                params = JSON.toJSONString(args);
            }

            //sysLog.setParams(params);
        } catch (Exception e) {
            log.error("sysLog,exception:{}", e, e);
        }
        //获取request
        HttpServletRequest request = HttpRequestUtil.getHttpServletRequest();
        //设置IP地址
        //sysLog.setIp(IPUtils.getIpAddr(request));
        log.info("Ip{}，接口地址{}，请求方式{}，入参：{}", IPUtils.getIpAddr(request), request.getRequestURL(), request.getMethod(), params);
        //用户名
//        String userId = httpSessionService.getCurrentUserId();
//        String username = httpSessionService.getCurrentUsername();
//        sysLog.setUsername(username);
//        sysLog.setUserId(userId);
//        sysLog.setTime((int) time);
//        log.info(sysLog.toString());
//        sysLogMapper.insert(sysLog);

    }
}
