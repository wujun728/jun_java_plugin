package com.mycompany.myproject.spring.xml;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class TestBean {
    public String name ;
    public int id ;

    public TestBean(){

    }

    public TestBean(int id, String name){

    }

    public void initMethod(){
        System.out.println("I am initMethod");
    }

    public void sayName(){
        System.out.println("I am TestBean");
    }

    public LookupMethodBean getLookupMethodBean(){
        return new LookupMethodBean("new LookupMethodBean");
    }

    public void overrideMethod(){
        System.out.println("overrideMethod");
    }

    public void getDefaultMemory(){
        int initMemory = SystemProperties.initMemory;
        System.out.println("depend on class MyDependOnBean init SystemProperties info : " + initMemory);
    }

    public void close(){
        System.out.println("spring destroy bean");
    }
}
