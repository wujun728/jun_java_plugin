package com.java8.annotation;



import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author BaoZhou
 * @date 2018/8/3
 */

public class AnnotationTest {

    @Test
    public void test1() throws NoSuchMethodException {
        Class<AnnotationTest> clazz = AnnotationTest.class;
        Method method = clazz.getMethod("show");
        MyAnnotation[] declaredAnnotations = method.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation annotation : declaredAnnotations) {
            System.out.println(annotation.value());
        }
    }
    @MyAnnotation("HELLO")
    @MyAnnotation("WORLD")
    public void show()
    {

    }
}
