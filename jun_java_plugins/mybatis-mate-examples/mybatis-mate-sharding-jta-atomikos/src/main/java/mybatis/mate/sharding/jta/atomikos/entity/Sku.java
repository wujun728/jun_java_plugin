package mybatis.mate.sharding.jta.atomikos.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * SKU 表
 */
@Getter
@Setter
public class Sku {
    // 主键 ID
    private Long id;
    // 商品
    private String product;
    // 颜色
    private String color;
    // 库存
    @TableField(update = "stock-#{et.stock}") // 注入自减指定值策略
    private Integer stock;

}
