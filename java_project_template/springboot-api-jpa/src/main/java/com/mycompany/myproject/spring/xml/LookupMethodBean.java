package com.mycompany.myproject.spring.xml;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class LookupMethodBean {

    private String xx;

    public LookupMethodBean(String xx){
        this.xx = xx;
    }

    public void syaName(){
        System.out.println("I am " + xx);
    }
}
