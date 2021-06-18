package com.lyx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:spring-dao.xml"});
        PersonService personService = (PersonService) context
                .getBean("personService");
        personService.addperson();
    }
}
