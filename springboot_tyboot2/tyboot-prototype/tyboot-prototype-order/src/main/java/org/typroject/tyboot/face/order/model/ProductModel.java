package org.typroject.tyboot.face.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品表 model
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编码
     */
    private String agencyCode;
    private String categoryCode;
    /**
     * 商品业务类型
     */
    private String productType;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品编号
     */
    private String productCode;
    /**
     * 商品原价
     */
    private BigDecimal originalPrice;
    /**
     * 锁定状态
     */
    private String lockStatus;
    /**
     * 锁定人
     */
    private String lockUserId;
    /**
     * 锁定时间
     */
    private Date lockDate;

    private List<ProductPriceModel> productPrice;
}
