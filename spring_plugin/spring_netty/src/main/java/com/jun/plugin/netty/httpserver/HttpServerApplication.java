package com.jun.plugin.netty.httpserver;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import com.jun.plugin.netty.httpserver.netty.annotation.NettyHttpHandler;

@SpringBootApplication()
@ComponentScan(includeFilters = @ComponentScan.Filter(NettyHttpHandler.class))
public class HttpServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HttpServerApplication.class).web(WebApplicationType.NONE).run(args);
    }

}
