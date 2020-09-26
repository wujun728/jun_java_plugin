package com.camel.server.route.dynamic.dynamic_1;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Properties;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectRouteA extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jetty:http://0.0.0.0:8282/dynamicRouterCamel")
                // 使用dynamicRouter，进行“动态路由”循环，
                // 直到指定的下一个元素为null为止
                .dynamicRouter().method(this, "doDirect").process(new Processor() {

            @Override
            public void process(Exchange exchange) throws Exception {
                System.out.println(exchange.getIn());
            }
        });
    }

    /**
     * 该方法用于根据“动态循环”的次数，确定下一个执行的Endpoint
     *
     * @param properties 通过注解能够获得的Exchange中properties属性，可以进行操作，并反映在整个路由过程中
     * @return
     */
    public String doDirect(@Properties Map<String, Object> properties) {

        System.out.println("properties : " + properties);

        // 在Exchange的properties属性中，取出Dynamic Router的循环次数
        AtomicInteger time = (AtomicInteger) properties.get("time");
        if (time == null) {
            time = new AtomicInteger(0);
            properties.put("time", time);
        } else {
            time = (AtomicInteger) time;
        }
        System.out.println(
                "这是Dynamic Router循环第：【" + time.incrementAndGet() + "】次执行！执行线程：" + Thread.currentThread().getName());

        // 第一次选择DirectRouteB
        if (time.get() == 1) {
            return "direct:directRouteB";
        }
        // 第二次选择DirectRouteC
        else if (time.get() == 2) {
            return "direct:directRouteC";
        }
        // 第三次选择一个Log4j-Endpoint执行
        else if (time.get() == 3) {
            return "log:DirectRouteA?showExchangeId=true&showProperties=ture&showBody=false";
        }

        // 其它情况返回null，终止 dynamicRouter的执行
        return null;
    }

}
