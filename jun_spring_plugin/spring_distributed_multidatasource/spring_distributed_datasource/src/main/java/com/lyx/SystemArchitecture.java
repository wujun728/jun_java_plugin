package com.lyx;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 在这里只定义pointcut
 *
 * @author Wujun
 */
@Aspect
public class SystemArchitecture {

    @Pointcut("@annotation(com.lyx.RequireDataSource)")
    public void requireDataSource() {
    }

    public void pointcut1() {
    }

    public void pointcut2() {
    }

    public void pointcut3() {
    }

    public void pointcut4() {
    }
}