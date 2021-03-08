package com.lyx;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class DetermineDataSourceAspect {

    public void determineDataSource(JoinPoint point) {
        System.out
                .println("determine datasource begin>>>>>>>>>>>>>>>>>>>>>>>>>");
        RequireDataSource datasource = ((MethodSignature) point.getSignature())
                .getMethod().getAnnotation(RequireDataSource.class);
        System.out.println("====>" + datasource.name());
        DbContextHolder.setDbType(datasource.name());
        System.out.println("determine datasource end>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
