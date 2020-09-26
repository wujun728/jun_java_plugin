package com.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author CYX
 */
public class AppCamelDemo12 {

    public static void main(String[] args) {

        try {
            ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

            // 通用没有具体业务意义的代码，只是为了保证主线程不退出
            synchronized (AppCamelDemo12.class) {
                AppCamelDemo12.class.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}