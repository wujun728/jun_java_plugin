package com.camel.server.route.dynamic.dynamic_1;

import org.apache.camel.builder.RouteBuilder;

public class DirectRouteC extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:directRouteC").to("log:DirectRouteC?showExchangeId=true&showProperties=ture&showBody=false");
    }

}
