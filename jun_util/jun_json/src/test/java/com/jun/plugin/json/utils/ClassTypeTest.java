package com.jun.plugin.json.utils;

import org.junit.Test;

public class ClassTypeTest {

    @Test
    public void testJacksonClassType() throws ClassNotFoundException {
        Class clazz = Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
        System.out.println("ClassTypeTest.testClassType: clazz ==> " + clazz.getName());
        System.out.println("ClassTypeTest.testClassType: clazz ==> " + clazz.getSimpleName());
        System.out.println("ClassTypeTest.testClassType: clazz ==> " + clazz.getTypeName());
        System.out.println("ClassTypeTest.testClassType: clazz ==> " + clazz.getCanonicalName());
        System.out.println("ClassTypeTest.testClassType: clazz ==> " + clazz.getClass());
    }

    @Test
    public void testGsonClassType() throws ClassNotFoundException {
        Class clazz = Class.forName("com.google.gson.Gson");
        System.out.println("ClassTypeTest.testGsonClassType: clazz ==> " + clazz.getName());
        System.out.println("ClassTypeTest.testGsonClassType: clazz ==> " + clazz.getSimpleName());
        System.out.println("ClassTypeTest.testGsonClassType: clazz ==> " + clazz.getTypeName());
        System.out.println("ClassTypeTest.testGsonClassType: clazz ==> " + clazz.getCanonicalName());
        System.out.println("ClassTypeTest.testGsonClassType: clazz ==> " + clazz.getClass());
    }

    @Test
    public void testFastjsonClassType() throws ClassNotFoundException {
        Class clazz = Class.forName("com.alibaba.fastjson.JSON");
        System.out.println("ClassTypeTest.testFastjsonClassType: clazz ==> " + clazz.getName());
        System.out.println("ClassTypeTest.testFastjsonClassType: clazz ==> " + clazz.getSimpleName());
        System.out.println("ClassTypeTest.testFastjsonClassType: clazz ==> " + clazz.getTypeName());
        System.out.println("ClassTypeTest.testFastjsonClassType: clazz ==> " + clazz.getCanonicalName());
        System.out.println("ClassTypeTest.testFastjsonClassType: clazz ==> " + clazz.getClass());
    }
}
