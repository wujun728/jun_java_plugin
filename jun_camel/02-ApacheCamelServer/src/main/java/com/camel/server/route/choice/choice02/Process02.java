package com.camel.server.route.choice.choice02;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author CYX
 * @create 2018-08-04-11:07
 */
public class Process02 implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Process02.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStream body = exchange.getIn().getBody(InputStream.class);
        String inputContext = IOUtils.toString(body, "UTF-8");

        System.out.println("Process02 -- : " + inputContext);

        // 存入到exchange的out区域
        if (exchange.getPattern() == ExchangePattern.InOut) {
            Message outMessage = exchange.getOut();
            outMessage.setBody(inputContext + " || 被Process02处理");
        }
    }

}