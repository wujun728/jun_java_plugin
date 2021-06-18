package org.typroject.tyboot.face.order.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * <p>
 * 产品价格表,通用的价格表,包括了书籍和餐品的价格
 * </p>
 *
 * @author Wujun
 * @since 2018-02-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_price" )
public class ProductPrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品外键
     */
    @TableField("PRODUCT_SEQ" )
    private Long productSeq;
    /**
     * 套餐/配置
     */
    @TableField("OPTIONS" )
    private String options;
    /**
     * 价格
     */
    @TableField("PRICE" )
    private BigDecimal price;


    public Long getProductSeq() {
        return productSeq;
    }

    public void setProductSeq(Long productSeq) {
        this.productSeq = productSeq;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
