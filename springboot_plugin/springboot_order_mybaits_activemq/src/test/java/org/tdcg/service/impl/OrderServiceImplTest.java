package org.tdcg.service.impl;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.tdcg.entity.*;
import org.tdcg.service.*;

import javax.jms.Queue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Title: OrderServiceImplTest
 * @Package: org.tdcg.service.impl
 * @Description:
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/31
 * @Version: V1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Before
    public void init(){

        //商品
        List<Goods> list = Lists.newArrayList(new Goods("1","1号商品",100),new Goods("2","2号商品",100),new Goods("3","3号商品",100));
        assert goodsService.insertBatch(list);

        //用户
        User user = new User();
        user.setId("1");
        user.setName("erdong");
        user.setAge(29);
        assert userService.insert(user);

        //店
        Shop shop = new Shop();
        shop.setId("1");
        shop.setName("1号店");
        assert shopService.insert(shop);
    }

    @Test
    public void insert(){

        Order order = new Order();
        order.setId("1");
        order.setStoreId("1");
        order.setUserId("1");
        order.setOrderStatus("unpay");
        order.setRemark("初始化订单，未支付");

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId("1");
        orderItem1.setOrderId(order.getId());
        orderItem1.setNum(3);
        orderItem1.setGoodsId("1");

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId("2");
        orderItem2.setOrderId(order.getId());
        orderItem2.setNum(2);
        orderItem2.setGoodsId("2");

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setId("3");
        orderItem3.setOrderId(order.getId());
        orderItem3.setNum(1);
        orderItem3.setGoodsId("3");

        boolean orderResult = orderService.insert(order);
        if (!orderResult) throw new AssertionError();

        List<OrderItem> list = Lists.newArrayList(orderItem1,orderItem2,orderItem3);
        boolean orderItemResult = orderItemService.insertBatch(list);
        if (!orderItemResult) throw new AssertionError();

        jmsMessagingTemplate.convertAndSend(queue, order);

        //消息消费参考OrderHandler
    }


    @After
    public void delete(){
        assert goodsService.deleteBatchIds(Arrays.asList("1","2","3"));
        assert userService.deleteById("1");
        assert shopService.deleteById("1");
        assert orderItemService.deleteBatchIds(Arrays.asList("1","2","3"));
        assert orderService.deleteById("1");
    }
}