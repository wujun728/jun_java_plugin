package com.xia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static final Logger log = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void test01(){
        log.debug("This is debug");
        log.info("This is info");
        log.warn("This is warn");
        log.error("This is error");
    }
}
