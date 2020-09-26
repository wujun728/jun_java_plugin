package com.camel.server.route.choice.choice02;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CYX
 * @create 2018-08-04-11:14
 */
public class ChoiceCamelRouteBuilder2 extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChoiceCamelRouteBuilder2.class);

    @Override
    public void configure() throws Exception {

        /**
         * 使用Jetty组件发布HTTP服务，接收消息
         *
         * 不论什么消息，都会进入Process01()方法。
         *
         * 当消息的Body包含字符串'yuanbao'、'yinwenjie'时：
         *
         * 当orgId == yuanbao，执行Process02
         * 当orgId == yinwenjie，执行Process03
         * 其它情况执行Process04
         */
        //from("jetty:http://0.0.0.0:8282/choiceCamel").process(new Process01())
        //        .choice()
        //        .when(body().contains("yuanbao")).process(new Process02())
        //        .when(body().contains("yinwenjie")).process(new Process03())
        //        .otherwise().process(new Process04())
        //        .endChoice();// 结束

        // =======================分割线=========================

        /**
         * 读取Json文件，对文件内容进行转换，接着判断对应字段值。
         * 注意：body是关键字
         */
        //from("file:./simple?noop=true")
        //        .unmarshal().json(JsonLibrary.Jackson, Order.class)
        //        .choice()
        //        .when().simple("${body.type} == 'yuanbao'").process(new Process05())
        //        .when().simple("${body.type} == 'yinwenjie'").process(new Process06())
        //        .otherwise().process(new Process07())
        //        .endChoice();// 结束

        // =======================分割线=========================

        /**
         * 使用Jetty组件，发布Http服务，就收消息，不论什么消息都会进入Process01
         *
         * 使用JsonPath表达式，从消息的body中提取orgid属性的值,存入header，然后判断。
         *
         * 当orgId == yuanbao，执行Process02
         * 当orgId == yinwenjie，执行Process03
         * 其它情况执行Process04
         */
        //org.apache.camel.model.language.JsonPathExpression jsonPathExpression =
        //        new org.apache.camel.model.language.JsonPathExpression("$.data.orgId");
        //
        //jsonPathExpression.setResultType(String.class);
        //System.out.println("jsonPathExpression : " + jsonPathExpression);
        //
        //from("jetty:http://0.0.0.0:8282/choiceCamel").process(new Process01())
        //        // 将orgId属性的值存储 exchange in Message的header中，以便后续进行判断
        //        .setHeader("orgId", jsonPathExpression)
        //        .choice()
        //        .when(header("orgId").isEqualTo("yuanbao")).process(new Process02())
        //        .when(header("orgId").isEqualTo("yinwenjie")).process(new Process03())
        //        .otherwise().process(new Process04())
        //        .endChoice();// 结束

        // =======================分割线=========================

        /**
         * 使用file组件，检查接收的文件名是否是指定的文件名
         * (不过，这个也可以用file组件中的filter属性来做(效率比这个好))
         */
        from("file:./simple?noop=true")
                .unmarshal().json(JsonLibrary.Jackson, Order.class)
                .choice()
                .when(header("CamelFileName").isEqualTo("simple.json")).process(new Process05())
                .when(header("CamelFileName").isEqualTo("simple2.json")).process(new Process06())
                .otherwise().process(new Process07())
                .endChoice();// 结束

    }

}
