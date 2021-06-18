package org.springsession.demo.javaconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class Config {

    /**
     * The Spring configuration is responsible for creating a Servlet Filter that
     * replaces the HttpSession implementation with an implementation backed by Spring Session.
     */
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName("192.168.1.149");
        connectionFactory.setPassword("123456");
        connectionFactory.setPort(6379);
        return connectionFactory;
    }

    /**
     * 让Spring Session不再执行config命令
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * Redis连接池
     * @return
     */
//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setPort(6379);
//        connectionFactory.setHostName("192.168.1.149");
//        connectionFactory.setPassword("123456");
//        return connectionFactory;
//    }
}