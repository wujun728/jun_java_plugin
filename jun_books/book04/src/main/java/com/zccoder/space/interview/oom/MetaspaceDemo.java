package com.zccoder.space.interview.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * java.lang.OutOfMemoryError: Metaspace 示例
 *
 * @author zc
 * @date 2020/05/05
 */
public class MetaspaceDemo {

    public static void main(String[] args) {

        // 指定JVM参数：-XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m

        // 模拟计数多少次以后发生异常
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OomTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        } catch (Throwable ex) {
            System.out.println("多少次后发生了异常：" + i);
            ex.printStackTrace();
        }
    }

    static class OomTest {
    }

}