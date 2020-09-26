package com.camel.server.route.choice.choice01;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.model.language.JsonPathExpression;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class ChoiceCamelRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// 这是一个JsonPath表达式，用于从http携带的json信息中，提取orgId属性的值
		JsonPathExpression jsonPathExpression = new JsonPathExpression("$.data.orgId");

		jsonPathExpression.setResultType(String.class);

		System.out.println("jsonPathExpression : " + jsonPathExpression);

		// 通用使用http协议接受消息
		from("jetty:http://0.0.0.0:8282/choiceCamel")
					// 首先送入HttpProcessor，
					// 负责将exchange in Message Body之中的stream转成字符串
					// 当然，不转的话，下面主要的choice操作也可以运行
					// HttpProcessor中的实现和上文代码片段中的一致，这里就不再重复贴出
					.process(new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {

							// 因为很明确消息格式是http的，所以才使用这个类
							// 否则还是建议使用org.apache.camel.Message这个抽象接口
							HttpMessage message = (HttpMessage) exchange.getIn();
							InputStream bodyStream = (InputStream) message.getBody();
							String inputContext = IOUtils.toString(bodyStream, "UTF-8");

							System.out.println("inputContext -- : " + inputContext);

							bodyStream.close();

							// 存入到exchange的out区域
							if (exchange.getPattern() == ExchangePattern.InOut) {
								Message outMessage = exchange.getOut();
								outMessage.setBody(inputContext + " || out");
							}
						}

					})
					// 将orgId属性的值存储 exchange in Message的header中，以便后续进行判断
					.setHeader("orgId", jsonPathExpression).choice()
					// 当orgId == yuanbao，执行OtherProcessor
					// 当orgId == yinwenjie，执行OtherProcessor2
					// 其它情况执行OtherProcessor3
					.when(header("orgId").isEqualTo("yuanbao")).process(new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {

							// 因为很明确消息格式是http的，所以才使用这个类
							// 否则还是建议使用org.apache.camel.Message这个抽象接口
							HttpMessage message = (HttpMessage) exchange.getIn();
							String body = message.getBody().toString();

							System.out.println("OtherProcessor body -- : " + body);

							// 存入到exchange的out区域
							if (exchange.getPattern() == ExchangePattern.InOut) {
								Message outMessage = exchange.getOut();
								outMessage.setBody(body + " || 被 orgId=yuanbao 的Processor处理");
							}

						}
					}).when(header("orgId").isEqualTo("yinwenjie")).process(new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {

							// 因为很明确消息格式是http的，所以才使用这个类
							// 否则还是建议使用org.apache.camel.Message这个抽象接口
							HttpMessage message = (HttpMessage) exchange.getIn();
							String body = message.getBody().toString();

							System.out.println("OtherProcessor2 body -- : " + body);
							// 存入到exchange的out区域
							if (exchange.getPattern() == ExchangePattern.InOut) {
								Message outMessage = exchange.getOut();
								outMessage.setBody(body + " || 被 orgId=yinwenjie 的Processor处理");
							}

						}
					}).otherwise().process(new Processor() {

						@Override
						public void process(Exchange exchange) throws Exception {

							// 因为很明确消息格式是http的，所以才使用这个类
							// 否则还是建议使用org.apache.camel.Message这个抽象接口
							HttpMessage message = (HttpMessage) exchange.getIn();
							String body = message.getBody().toString();

							System.out.println("OtherProcessor3 body -- : " + body);

							// 存入到exchange的out区域
							if (exchange.getPattern() == ExchangePattern.InOut) {
								Message outMessage = exchange.getOut();
								outMessage.setBody(body + " || 被OtherProcessor处理");
							}

						}
					})
					// 结束
					.endChoice();
	}

}
