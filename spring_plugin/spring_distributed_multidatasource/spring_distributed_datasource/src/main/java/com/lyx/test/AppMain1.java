package com.lyx.test;

import java.lang.reflect.Field;

/**
 * Created by Lenovo on 2014/12/4.
 */
public class AppMain1 {

    public static void main(String args[]) throws NoSuchFieldException {

        Company company = new Company();
        company.setName("usoft");
        company.setAddress("unknow");

        Class<Company> companyClass = (Class<Company>) company.getClass();

        MyClassAnnotation myClassAnnotation = companyClass
            .getAnnotation(MyClassAnnotation.class);
        System.out.println(myClassAnnotation.uri());
        System.out.println(myClassAnnotation.desc());

        Field field = companyClass.getDeclaredField("name");
        System.out.println(field.toString());

        Field[] fields = companyClass.getFields();
        for (Field f : fields) {
        }
    }
}
