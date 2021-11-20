package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tqlin.service.IDemoService;

@Controller
@RequestMapping("demoWeb")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    IDemoService demoService;

    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer-web.xml"});
        context.start();
        System.out.println("web端消费方（Consumer）启动......");
        IDemoService demoService = (IDemoService) context.getBean("demoService"); // get remote service proxy
        System.out.println("消费方（Consumer）");
        while (true) {
            try {
                Thread.sleep(2000);
                String hello = demoService.sayHello("第1个：我是web端"); // call remote method
                System.out.println(hello); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


        }

    }
}
