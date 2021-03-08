package com.zh.springbootasync.service.impl;

import com.zh.springbootasync.service.MsgService;
import com.zh.springbootasync.service.OrderService;
import com.zh.springbootasync.utils.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @date 2019/6/17
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MsgService msgService;

    /**
     * 无返回值纯异步调用
     */
    @Override
    public void createOrder() {
        log.info("============订单开始成功=============");
        this.msgService.sendMsg();
        SpringContext.getBean(OrderService.class).sendEmail();
        log.info("============订单创建成功=============");
    }
    @Async
    @Override
    public void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(5);
            log.info("============email发送成功=============");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 有返回值异步调用，Future<T>接收，while(true)轮训结果
     */
    @Override
    public void cancelOrder() throws ExecutionException, InterruptedException {
        log.info("============订单开始取消=============");
        Future<String> future1 = SpringContext.getBean(OrderService.class).doCancelTask1();
        Future<String> future2 = SpringContext.getBean(OrderService.class).doCancelTask2();
        Future<String> future3 = SpringContext.getBean(OrderService.class).doCancelTask3();
        long startTime = System.currentTimeMillis();
        while (true){
            if (future1.get().equals("OK") && future2.get().equals("OK") && future3.get().equals("OK")){
                break;
            }
        }
        long costTime = System.currentTimeMillis() - startTime;
        log.info("============订单取消成功,耗时:{}ms=============",costTime);
    }

    @Async
    @Override
    public Future<String> doCancelTask1() {
        try {
            log.info("============doCancelTask1开始=============");
            TimeUnit.SECONDS.sleep(5);
            log.info("============doCancelTask1结束=============");
            return new AsyncResult<>("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return new AsyncResult<>("FAIL");
        }
    }

    @Async
    @Override
    public Future<String> doCancelTask2() {
        try {
            log.info("============doCancelTask2开始=============");
            TimeUnit.SECONDS.sleep(7);
            log.info("============doCancelTask2结束=============");
            return new AsyncResult<>("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return new AsyncResult<>("FAIL");
        }
    }

    @Async
    @Override
    public Future<String> doCancelTask3() {
        try {
            log.info("============doCancelTask3开始=============");
            TimeUnit.SECONDS.sleep(10);
            log.info("============doCancelTask3结束=============");
            return new AsyncResult<>("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return new AsyncResult<>("FAIL");
        }
    }

    /**
     * 有返回值异步调用，java8的CompletableFuture<T>接收
     */
    @Override
    public void deleteOrder() throws ExecutionException, InterruptedException {
        log.info("============订单开始删除=============");
        CompletableFuture<String> future1 = SpringContext.getBean(OrderService.class).doDeleteTask1();
        CompletableFuture<String> future2 = SpringContext.getBean(OrderService.class).doDeleteTask2();
        CompletableFuture<String> future3 = SpringContext.getBean(OrderService.class).doDeleteTask3();
        long startTime = System.currentTimeMillis();
        CompletableFuture.allOf(future1,future2,future3).join();
        long costTime = System.currentTimeMillis() - startTime;
        if (future1.get().equals("OK") && future2.get().equals("OK") && future3.get().equals("OK")){
            log.info("============订单删除成功,耗时:{}ms=============",costTime);
        }
    }

    @Async
    @Override
    public CompletableFuture<String> doDeleteTask1() {
        try {
            log.info("============doDeleteTask1开始=============");
            TimeUnit.SECONDS.sleep(5);
            log.info("============doDeleteTask1结束=============");
            return CompletableFuture.completedFuture("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return CompletableFuture.completedFuture("FAIL");
        }
    }

    @Async
    @Override
    public CompletableFuture<String> doDeleteTask2() {
        try {
            log.info("============doDeleteTask2开始=============");
            TimeUnit.SECONDS.sleep(7);
            log.info("============doDeleteTask2结束=============");
            return CompletableFuture.completedFuture("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return CompletableFuture.completedFuture("FAIL");
        }
    }

    @Async
    @Override
    public CompletableFuture<String> doDeleteTask3() {
        try {
            log.info("============doDeleteTask3开始=============");
            TimeUnit.SECONDS.sleep(10);
            log.info("============doDeleteTask3结束=============");
            return CompletableFuture.completedFuture("OK");
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            return CompletableFuture.completedFuture("FAIL");
        }
    }

}
