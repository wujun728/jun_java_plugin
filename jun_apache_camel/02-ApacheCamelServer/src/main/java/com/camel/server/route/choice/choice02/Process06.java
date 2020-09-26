package com.camel.server.route.choice.choice02;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CYX
 * @create 2018-08-04-11:08
 */
public class Process06 implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Process06.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        System.out.println("=====Process06=====");
        System.out.println("Process06 - " + exchange.toString());
        System.out.println("=====Process06=====");

    }
}
