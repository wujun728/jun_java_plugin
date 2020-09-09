package com.tanghd.thrift.netty.load.test.service;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServiceProcessor implements TestService.Iface {

    private static final Logger log = LoggerFactory.getLogger(TestServiceProcessor.class);

    @Override
    public String test(String msg) throws TException {
        log.info(msg);
        try{
            TimeUnit.MILLISECONDS.sleep(50);//模拟睡50毫秒
        }catch(Exception e){
            
        }
        return "Hello " + msg;
    }

}
