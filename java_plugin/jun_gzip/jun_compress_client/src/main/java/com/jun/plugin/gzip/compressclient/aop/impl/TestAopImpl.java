package com.jun.plugin.gzip.compressclient.aop.impl;

import org.springframework.stereotype.Component;

import com.jun.plugin.gzip.compressclient.aop.TestAop;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 16:13
 */
@Component
public class TestAopImpl implements TestAop {

    @Override
    public String testSpringBootAop(String str) {
        return "Hello World.";
    }
}
