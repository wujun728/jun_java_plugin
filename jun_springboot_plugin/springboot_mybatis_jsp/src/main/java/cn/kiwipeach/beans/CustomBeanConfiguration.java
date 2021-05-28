package cn.kiwipeach.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Create Date: 2017/11/05
 * Description: Bean工厂
 *
 * @author Wujun
 */
@Configuration
public class CustomBeanConfiguration {

    @Bean
    public Apple newInstanceApple(){
        Apple apple = new Apple();
        apple.setName("红富士-苹果");
        apple.setPrice(new BigDecimal(9.87));
        return apple;
    }

    @Bean
    public Banana newInstanceBanana(){
        Banana banana = new Banana();
        banana.setName("广东-大香蕉");
        banana.setPrice(new BigDecimal(9.87));
        return banana;
    }

}
