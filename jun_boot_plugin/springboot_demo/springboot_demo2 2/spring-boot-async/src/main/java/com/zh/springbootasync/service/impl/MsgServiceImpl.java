package com.zh.springbootasync.service.impl;

import com.zh.springbootasync.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @date 2019/6/17
 */
@Slf4j
@Service
public class MsgServiceImpl implements MsgService{

    @Async
    @Override
    public void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(10);
            log.info("============消息发送成功=============");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }
}
