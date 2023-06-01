package com.mycompany.myproject.spring.xml;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @auther barry
 * @date 2019/2/14
 */
public class XmlTest {
    
    
    public static void main(String[] args) {

        //Deprecated
        //XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));

        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory(null);

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("beans.xml"));

        TestBean testBean = beanFactory.getBean("testBean", TestBean.class);
        testBean.sayName();

        testBean.getLookupMethodBean().syaName();

        testBean.overrideMethod();

        testBean.getDefaultMemory();

        Object object = beanFactory.getBean("myObjectFactory");
        System.out.println(object);

        Object myObject = beanFactory.getBean("myObject");
        System.out.println(myObject);

        Object myCustomObject = beanFactory.getBean("myCustomObject");
        System.out.println(myCustomObject);


        Person person1 = beanFactory.getBean("person1", Person.class);
        System.out.println(person1);
        Person person2 = beanFactory.getBean("person1", Person.class);
        System.out.println(person2);
        Person person3 = beanFactory.getBean("person1", Person.class);
        System.out.println(person3);


        MyService myService = beanFactory.getBean(MyService.class);
        myService.test();

        Address address1 = beanFactory.getBean("laiquanAddress", Address.class);
        System.out.println(address1);

        Address address2 = beanFactory.getBean("hongquanAddress", Address.class);
        System.out.println(address2);

        //destroy-method
        beanFactory.destroySingletons();
    }
}
