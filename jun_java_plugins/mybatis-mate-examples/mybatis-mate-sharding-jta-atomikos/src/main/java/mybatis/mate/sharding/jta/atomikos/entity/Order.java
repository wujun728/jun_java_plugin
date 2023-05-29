package mybatis.mate.sharding.jta.atomikos.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 测试订单
 */
@Getter
@Setter
@TableName("t_order")
public class Order {
    // 主键 ID
    private Long id;
    // SKU ID
    private Long skuId;
    // 数量
    private Integer quantity;
    // 价格
    private BigDecimal price;

}
