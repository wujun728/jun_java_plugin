package com.zh.springbootasync;

import com.zh.springbootasync.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootAsyncApplicationTests {

    @Autowired
    private OrderService orderService;

    /**
     * 测试无返回值纯异步方式
     * 涉及到本类异步和其他类异步，调用方式不同
     * 调用实现了接口的其他类的异步方法，直接调用
     * 调用实现了接口的本类异步方法需要获得本类的class对象在调用，否则不成功
     * @throws InterruptedException
     */
    @Test
    public void createOrderTest() throws InterruptedException {
        orderService.createOrder();
        TimeUnit.SECONDS.sleep(20);
    }

    /**
     * 测试有返回值的异步
     * 需要用Future<T>接收返回值，然后while(true)轮训结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void cancelOrderTest() throws ExecutionException, InterruptedException {
        orderService.cancelOrder();
    }

    /**
     * 测试有返回值的异步
     * 需要用CompletableFuture<T>接收返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void deleteOrderTest() throws ExecutionException, InterruptedException {
        orderService.deleteOrder();
    }
}
