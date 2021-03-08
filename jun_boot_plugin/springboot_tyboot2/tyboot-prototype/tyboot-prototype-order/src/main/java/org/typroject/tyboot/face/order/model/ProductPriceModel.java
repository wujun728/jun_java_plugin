package org.typroject.tyboot.face.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;

/**
 * <p>
 * 产品价格表,通用的价格表,包括了书籍和餐品的价格 model
 * </p>
 *
 * @author Wujun
 * @since 2018-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPriceModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 商品外键
     */
    private Long productSeq;
    /**
     * 套餐/配置
     */
    private String options;
    /**
     * 价格
     */
    private BigDecimal price;
}
