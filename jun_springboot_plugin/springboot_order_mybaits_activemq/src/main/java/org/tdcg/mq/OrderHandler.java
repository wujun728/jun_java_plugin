package org.tdcg.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.tdcg.entity.Goods;
import org.tdcg.entity.Order;
import org.tdcg.entity.OrderItem;
import org.tdcg.service.GoodsService;
import org.tdcg.service.OrderItemService;
import org.tdcg.service.OrderService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.List;

@Component
public class OrderHandler {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

	@JmsListener(destination = "activemq.order", concurrency = "5-10")
	public void receiveQueue(@Payload Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage msg = (ObjectMessage) message;
			try {
				Order order = (Order)msg.getObject();
                System.out.println("消费消息 order,id为： " + order.getId());
                List<OrderItem> orderItems = orderItemService.selectByOrderId(order.getId());
                orderItems.forEach(orderItem -> {
                    Goods goods = goodsService.selectById(orderItem.getGoodsId());
                    goods.setInventory(goods.getInventory() - orderItem.getNum());
                    goodsService.updateById(goods);
                });
            } catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
