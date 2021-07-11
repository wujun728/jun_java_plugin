package com.buxiaoxia.service;

import com.buxiaoxia.business.entity.Order;
import com.buxiaoxia.business.entity.OrderItem;
import com.buxiaoxia.business.repository.OrderItemRepository;
import com.buxiaoxia.business.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaw
 * @date 2018/4/19 11:18
 * Description:
 */
@Service
public class OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    public void demo() {
        List<Long> orderIds = new ArrayList<>(10);
        List<Long> orderItemIds = new ArrayList<>(10);
        System.out.println("1.Insert--------------");
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setUserId(51);
            order.setStatus("INSERT_TEST");
            long orderId = orderRepository.save(order).getOrderId();
            orderIds.add(orderId);
            OrderItem item = new OrderItem();
            item.setOrderId(orderId);
            item.setUserId((int)(Math.random()*100));
            orderItemIds.add(orderItemRepository.save(item).getOrderItemId());
        }
        List<OrderItem> orderItems = orderItemRepository.findAll();
        System.out.println(orderItemRepository.findAll());
        System.out.println("2.Delete--------------");
        if (orderItems.size() > 0) {
            for (Long each : orderItemIds) {
                orderItemRepository.delete(each);
            }
            for (Long each : orderIds) {
                orderRepository.delete(each);
            }
        }
        System.out.println(orderItemRepository.findAll());
    }

}
