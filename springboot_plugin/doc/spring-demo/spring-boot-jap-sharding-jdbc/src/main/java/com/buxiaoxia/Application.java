package com.buxiaoxia;

import com.buxiaoxia.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author xiaw
 * @date 2018/4/19 10:44
 * Description:
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        applicationContext.getBean(OrderService.class).demo();
    }

}
