package com.designpatterns.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class IUserDaoDynamicProxy implements InvocationHandler {
    private Object target;

    public IUserDaoDynamicProxy(Object target) {
        this.target = target;
    }

    public Object bind() {
        Class clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    /**
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object argObject = args[0];
        beforeMethod(argObject);
        Object object = method.invoke(target,args);
        afterMethod();
        return object;
    }

    private void beforeMethod(Object object) {
        int userId = 0;
        if (object instanceof Integer) {
            userId = (int) object;
        }
        System.out.println("动态代理 before method userid:" +userId);

    }


    private void afterMethod() {
        int userId = 0;
        System.out.println("动态代理 after method");
    }
}
