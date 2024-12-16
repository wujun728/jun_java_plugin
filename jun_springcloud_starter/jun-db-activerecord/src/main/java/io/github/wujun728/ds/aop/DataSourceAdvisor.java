package io.github.wujun728.ds.aop;

import java.lang.reflect.Method;

import io.github.wujun728.ds.datasource.DynamicDataSource;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;


/**
 * 有{ DataSourceChange}注解的方法，调用时会切换到指定的数据源
 * @author tanghd
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
