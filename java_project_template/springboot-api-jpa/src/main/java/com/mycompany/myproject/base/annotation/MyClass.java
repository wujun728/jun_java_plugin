package com.mycompany.myproject.base.annotation;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.lang.annotation.Annotation;

/**
 * @auther barry
 * @date 2019/2/13
 *
 * https://www.cnblogs.com/hujunzheng/p/9790588.html
 */
@MyClassAnnotation
public class MyClass {


    @MyMethodAnnotation
    public void test(){

    }



    public static void main(String[] args){

        MyClass myClass = new MyClass();


        Annotation[] annotations = MyClass.class.getAnnotations();

        Arrays.asList(annotations).forEach(a ->{
            System.out.println(a.annotationType());
        });


        Method[] methods = MyClass.class.getMethods();

        Arrays.asList(methods).forEach(m -> {
            Annotation[] mas = m.getAnnotations();
            Arrays.asList(mas).forEach(ma ->{
                System.out.println(ma.annotationType());

            });
        });
    }
}
