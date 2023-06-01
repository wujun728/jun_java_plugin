package com.jun.plugin.test;

import org.junit.Test;

import com.jun.plugin.common.exception.CustomException;

public class SpringMyBatisTest {

    /*@Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookServiceImpl = (BookService) applicationContext.getBean("BookServiceImpl");
        System.out.println(bookServiceImpl.queryAllBooks());
    }*/

    @Test
    public void test01() {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            throw new CustomException(500, "错误错误");
        }
    }

}
