package com.mycompany.myproject.spring.xml;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class MyMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {

        System.out.println("I am MyMethodReplacer");

        return null;
    }
}
