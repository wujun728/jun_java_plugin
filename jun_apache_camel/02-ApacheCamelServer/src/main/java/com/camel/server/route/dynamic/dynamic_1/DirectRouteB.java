package com.camel.server.route.dynamic.dynamic_1;

import org.apache.camel.builder.RouteBuilder;

public class DirectRouteB extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:directRouteB").to("log:DirectRouteB?showExchangeId=true&showProperties=ture&showBody=false");
    }

}
