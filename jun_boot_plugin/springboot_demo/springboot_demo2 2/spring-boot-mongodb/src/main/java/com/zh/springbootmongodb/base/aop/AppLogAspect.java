package com.zh.springbootmongodb.base.aop;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.zh.springbootmongodb.base.enums.AppResultCode;
import com.zh.springbootmongodb.base.utils.IpUtil;
import com.zh.springbootmongodb.service.AppVisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Slf4j
@Aspect
@Component
public class AppLogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AppVisitLogService appVisitLogService;

    public static final ThreadLocal<String> REQUEST_UUID = new ThreadLocal<>();

    public static final ThreadLocal<Long> REQUEST_START_TIME = new ThreadLocal<>();

    @Pointcut("execution( * com.zh.springbootmongodb.controller.*.*(..))")
    public void appLogPointCut() {}

    @Before("appLogPointCut()")
    public void doBefore(JoinPoint joinPoint){
        log.info("====================before拦截开启====================");
        String url = this.request.getRequestURL().toString();
        String requestMethod = this.request.getMethod();
        Signature signature = joinPoint.getSignature();
        String clazz = signature.getDeclaringTypeName();
        String method = signature.getName();
        Map<String, String[]> map =  this.request.getParameterMap();
        String params = JSONObject.toJSONString(map.size() == 0 ? null : map);
        String uuid = IdUtil.simpleUUID();
        String ipAddress = IpUtil.getIpAddress(this.request);
        String userAgent = this.request.getHeader("User-Agent");
        Date requestTime = new Date();
        REQUEST_UUID.set(uuid);
        REQUEST_START_TIME.set(requestTime.getTime());
        this.appVisitLogService.save(uuid,ipAddress,userAgent,url,clazz,method,params,requestTime);
        log.info("=====================uuid:{},请求路径:{}========================",uuid,url);
        log.info("=====================uuid:{},请求方法:{}========================",uuid,requestMethod);
        log.info("=====================uuid:{},请求类全路径:{}========================",uuid,clazz + "." + method);
        log.info("=====================uuid:{},请求参数:{}========================",uuid,params);
    }

    @AfterReturning(pointcut = "appLogPointCut()", returning = "ret")
    public void doAfterReturning(Object ret){
        log.info("====================afterReturning拦截开启====================");
        String result = JSONObject.toJSONString(ret);
        String uuid = REQUEST_UUID.get();
        Date responseTime = new Date();
        long costTime = responseTime.getTime() - REQUEST_START_TIME.get();
        this.appVisitLogService.save(uuid,responseTime,costTime,result, AppResultCode.SUCCESS.getCode());
        log.info("=====================uuid:{},耗时:{}ms,返回结果:{}========================",uuid,costTime,result);
    }

    @AfterThrowing(pointcut = "appLogPointCut()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        log.info("====================afterThrowing拦截开启====================");
        String uuid = REQUEST_UUID.get();
        String errorMsg = ex.getMessage();
        log.info("====================uuid:{},出现异常:{}====================",uuid,errorMsg);
    }

    @Around("appLogPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====================around拦截开启====================");
        log.info("====================around前置做某些事====================");
        Object result = joinPoint.proceed();
        log.info("====================around后置做某些事====================");
        return result;
    }
}
