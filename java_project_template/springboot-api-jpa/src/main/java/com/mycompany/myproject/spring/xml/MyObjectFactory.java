package com.mycompany.myproject.spring.xml;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @auther barry
 * @date 2019/2/18
 */
public class MyObjectFactory implements ObjectFactory {
    @Override
    public Object getObject() throws BeansException {
        return "MyObject";
    }

    public Object customObject(){
        return "MyCustomObject";
    }
}
