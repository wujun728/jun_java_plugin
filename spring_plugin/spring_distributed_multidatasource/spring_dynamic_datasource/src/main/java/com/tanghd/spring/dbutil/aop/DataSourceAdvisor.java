package com.tanghd.spring.dbutil.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.tanghd.spring.dbutil.datasource.DynamicDataSource;

/**
 * 有{@link com.tanghd.spring.dbutil.aop.DataSourceChange}注解的方法，调用时会切换到指定的数据源
 * 
 * @author Wujun
 *
 */
public class DataSourceAdvisor implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        if (null != annotation) {
            DynamicDataSource.reset();
        }
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        if (null != annotation) {
            DynamicDataSource.reset();
        }
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        DataSourceChange annotation = method.getAnnotation(DataSourceChange.class);
        if (null != annotation) {
            if (annotation.slave()) {
                DynamicDataSource.useSlave();
            } else {
                DynamicDataSource.useMaster();
            }
        }
    }

}
