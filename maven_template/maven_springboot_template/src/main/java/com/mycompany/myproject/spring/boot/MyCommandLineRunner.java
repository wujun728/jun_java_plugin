package com.mycompany.myproject.spring.boot;

import com.mycompany.myproject.base.TestBean;
import com.mycompany.myproject.spring.util.SpringContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyCommandLineRunner implements CommandLineRunner , ApplicationContextAware {

    public ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Custom CommandLineRunner :  MyCommandLineRunner.run");

        // 启动 ICE

        TestBean testBean = new TestBean();
        testBean.init();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}
