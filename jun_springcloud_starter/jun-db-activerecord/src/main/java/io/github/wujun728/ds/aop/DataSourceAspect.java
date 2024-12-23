package io.github.wujun728.ds.aop;

import java.lang.reflect.Method;

import io.github.wujun728.ds.datasource.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * 有{ DataSourceChange}注解的方法，调用时会切换到指定的数据源
 * 
 * @author tanghd
 *
 */
@Aspect
public class DataSourceAspect {

    @Pointcut(value = "@annotation(io.github.wujun728.ds.aop.DataSourceChange)")
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
                if (annotation.dsname().equalsIgnoreCase(DynamicDataSource.DEFAULT)) {
                    DynamicDataSource.useMain();
                } else {
                    DynamicDataSource.useSlave();
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
