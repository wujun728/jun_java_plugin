package com.mycompany.myproject.spring.xml;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class MyDependOnBean {



    public MyDependOnBean(int value){
        SystemProperties.setInitmemory(value);
    }
}
