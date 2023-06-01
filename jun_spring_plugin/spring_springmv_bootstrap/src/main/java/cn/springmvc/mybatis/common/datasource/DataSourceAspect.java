package cn.springmvc.mybatis.common.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 数据源切面类
 * 
 */
public class DataSourceAspect {

    /**
     * 
     * 在数据库调用之前进行数据源设置 注：<br/>
     * 对于数据源设置采取就近原则： <br/>
     * 1、如果方法上有数据源设置则以方法为准 <br/>
     * 2、如果方法上没有进行数据源设置则以类上的数据源设置为准
     */
    public void before(JoinPoint joinPoint) {
        // 目标类
        Object target = joinPoint.getTarget();
        // 目标类Class字节码
        Class<?> targetClass = target.getClass();
        // 目标方法的签名
        Signature signature = joinPoint.getSignature();
        // 目标方法名
        String methodName = signature.getName();
        // 目标方法的参数类型数组
        Class<?>[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();

        try {
            DataSource dataSource = null;
            Method method = targetClass.getMethod(methodName, parameterTypes);
            if (method != null && method.isAnnotationPresent(DataSource.class)) // 获取方法上的数据源设置
                dataSource = method.getAnnotation(DataSource.class);
            else if (targetClass.isAnnotationPresent(DataSource.class)) // 获取类上的数据源设置
                dataSource = targetClass.getAnnotation(DataSource.class);

            if (dataSource != null) {
                DataSourceEnum dataSourceName = dataSource.value();
                String key = dataSourceName.getKey();
                DynamicDataSourceHolder.setDataSourceKey(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
