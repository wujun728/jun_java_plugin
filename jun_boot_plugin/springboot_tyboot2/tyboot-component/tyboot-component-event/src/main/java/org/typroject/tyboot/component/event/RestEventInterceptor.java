package org.typroject.tyboot.component.event;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.context.RequestContextModel;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.lang.reflect.Method;

/**
 * <pre>
 *
 *  Tyrest
 *  File: RestEventInterceptor.java
 *
 *  Tyrest, Inc.
 *  Copyright (C): 2015
 *
 *  Description:系统的业务事件拦截器
 *  TODO 用于拦截所有的业务事件，并交给监听器处理
 *
 *  Notes:
 *  $Id: RestEventInterceptor.java 31101200-9 2014-10-14 16:43:51Z Tyrest\magintursh $
 *
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年6月23日		magintursh		Initial.
 *
 * </pre>
 */
@Aspect
@Order(value = 1000)
@Component
public class RestEventInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RestEventInterceptor.class);

    @Autowired
    private ApplicationContext applicationContext;

    /*定义切入点，所有那些触发业务事件的方法都会成为切入点*/
    @Pointcut("@annotation(org.typroject.tyboot.component.event.RestEventTrigger)")
    public void restEventOccured() {
    }

    /*在触发业务事件的方法执行完之后触发系统业务事件*/
    @Around("restEventOccured()")
    public Object fireEvent(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = pjp.proceed();
        this.publishEvent(pjp,retVal);
        return retVal;
    }

    /**
     * 根据系统业务事件类型发布系统事件
     * TODO.
     * @param pjp
     */
    private void publishEvent(ProceedingJoinPoint pjp,Object retVal) {
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method   = methodSignature.getMethod();
            Object []params = pjp.getArgs();
            RestEventTrigger eventTrigger   = method.getAnnotation(RestEventTrigger.class);
            //如果正在执行的方法上有TyrestEventTrigger注解，说明该方法是一个系统业务事件的触发器
            if (!ValidationUtil.isEmpty(eventTrigger)) {
                String[] events = eventTrigger.value();
                if (!ValidationUtil.isEmpty(events)) {
                    Object eventSourceData  = null;
                    for (String event : events) {
                        eventSourceData     = RestEventHandler.obtainEventSource(event);

                        if(ValidationUtil.isEmpty(eventSourceData))
                        {
                            eventSourceData = retVal;
                        }
                        // 根据不同的事件类型，构造不同的事件处理器所需要的事件源数据
                        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                        String lable = eventTrigger.label();
                        if(!ValidationUtil.isEmpty(apiOperation))
                            lable = apiOperation.value();

                        RequestContextModel contextModel = RequestContext.cloneRequestContext();
                        applicationContext.publishEvent(new RestEvent(event, params,eventSourceData,contextModel, method.getName(),lable));
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}

/*
*$Log: av-env.bat,v $
*/