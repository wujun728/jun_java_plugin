package com.alibaba.dubbo;

import com.alibaba.boot.dubbo.EnableDubboProxy;
import com.alibaba.boot.dubbo.websocket.GenericProxyHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by wuyu on 2017/4/22.
 */
@SpringBootApplication
@EnableDubboProxy
@EnableWebSocket
public class DubboApplication{

    public static void main(String[] args) {
        SpringApplication.run(DubboApplication.class, args);
    }


    /**
     * ws = new WebSocket("ws://localhost:8100/ws")
     * ws.onmessage= function(event){console.log(event)}
     * ws.send('{\"params\":[1],\"method\":\"com.example.service.UserService.selectByPrimaryKey\",\"version\":\"1.0.0\"}')
     */
    @Configuration
    class WebSocketConfig  implements WebSocketConfigurer{
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(genericProxyHandler(),"/ws");
        }

        @Bean
        public GenericProxyHandler genericProxyHandler() {
            return new GenericProxyHandler();
        }
    }

}
