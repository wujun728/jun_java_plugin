package com.mycompany.myproject.base;


import com.mycompany.myproject.spring.util.SpringContextUtil;
import org.springframework.context.ApplicationContext;

public class TestBean {


    public void init(){

        Object obj = SpringContextUtil.getBean("dataSource");

    }

    public void init(ApplicationContext applicationContext){

        Object obj = applicationContext.getBean("dataSource");

    }

}
