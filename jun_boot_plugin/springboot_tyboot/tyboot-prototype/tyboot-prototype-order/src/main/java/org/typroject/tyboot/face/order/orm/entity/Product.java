package org.typroject.tyboot.face.order.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author Wujun
 * @since 2018-01-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product" )
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商家编码
     */
    @TableField("AGENCY_CODE" )
    private String agencyCode;
    @TableField("CATEGORY_CODE" )
    private String categoryCode;
    /**
     * 商品业务类型
     */
    @TableField("PRODUCT_TYPE" )
    private String productType;
    /**
     * 商品名称
     */
    @TableField("PRODUCT_NAME" )
    private String productName;

    /**
     * 商品编号
     */
    @TableField("PRODUCT_CODE" )
    private String productCode;
    /**
     * 商品原价
     */
    @TableField("ORIGINAL_PRICE" )
    private BigDecimal originalPrice;
    /**
     * 锁定状态
     */
    @TableField("LOCK_STATUS" )
    private String lockStatus;
    /**
     * 锁定人
     */
    @TableField("LOCK_USER_ID" )
    private String lockUserId;
    /**
     * 锁定时间
     */
    @TableField("LOCK_DATE" )
    private Date lockDate;
}
