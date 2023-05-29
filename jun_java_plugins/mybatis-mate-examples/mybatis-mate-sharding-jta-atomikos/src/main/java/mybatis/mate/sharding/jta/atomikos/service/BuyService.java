package mybatis.mate.sharding.jta.atomikos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BuyService {
    public OrderService orderService;
    public SkuService skuService;
    public LogService logService;

    @Transactional(rollbackFor = Exception.class)
    public String buy(boolean error) {
        final Long skuId = 3L;
        final Integer quantity = 1;

        // 数据库 test 创建订单
        orderService.createOrder(skuId, quantity);

        // 数据库 test2 减掉库存
        skuService.reduceStock(skuId, quantity, error);

        // 数据库 test3 记录日志
        logService.save("订单 123 下单成功");
        return "ok";
    }
}
