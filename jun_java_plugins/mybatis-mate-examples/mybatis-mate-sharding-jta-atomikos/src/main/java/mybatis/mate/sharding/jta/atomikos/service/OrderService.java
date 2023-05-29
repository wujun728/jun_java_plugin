package mybatis.mate.sharding.jta.atomikos.service;

import mybatis.mate.sharding.jta.atomikos.entity.Order;
import mybatis.mate.sharding.jta.atomikos.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
    @Autowired
    public OrderMapper orderMapper;

    /**
     * 下单
     */
    public boolean createOrder(Long skuId, Integer quantity) {
        Order order = new Order();
        order.setSkuId(skuId);
        order.setQuantity(quantity);
        order.setPrice(BigDecimal.valueOf(6000));
        return orderMapper.insert(order) > 0;
    }
}
