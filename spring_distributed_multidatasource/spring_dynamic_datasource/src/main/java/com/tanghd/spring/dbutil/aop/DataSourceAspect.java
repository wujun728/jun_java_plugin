package com.tanghd.spring.dbutil.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.tanghd.spring.dbutil.datasource.DynamicDataSource;

/**
 * 有{@link com.tanghd.spring.dbutil.aop.DataSourceChange}注解的方法，调用时会切换到指定的数据源
 * 
 * @author tanghd
 *
 */
@Aspect
public class DataSourceAspect {

    @Pointcut(value = "@annotation(com.tanghd.spring.dbutil.aop.DataSourceChange)")
    private void changeDS() {
    }

    @Around(value = "changeDS() ", argNames = "pjp")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        boolean selectedDataSource = false;
        try {
            if (null != annotation) {
                selectedDataSource = true;
                if (annotation.slave()) {
                    DynamicDataSource.useSlave();
                } else {
                    DynamicDataSource.useMaster();
                }
            }
            retVal = pjp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }
}
